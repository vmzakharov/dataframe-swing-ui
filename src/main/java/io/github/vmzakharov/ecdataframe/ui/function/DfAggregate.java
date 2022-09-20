package io.github.vmzakharov.ecdataframe.ui.function;

import io.github.vmzakharov.ecdataframe.dataframe.AggregateFunction;
import io.github.vmzakharov.ecdataframe.dataframe.DataFrame;
import io.github.vmzakharov.ecdataframe.dsl.function.IntrinsicFunctionDescriptor;
import io.github.vmzakharov.ecdataframe.dsl.value.DataFrameValue;
import io.github.vmzakharov.ecdataframe.dsl.value.Value;
import io.github.vmzakharov.ecdataframe.dsl.value.ValueType;
import io.github.vmzakharov.ecdataframe.dsl.value.VectorValue;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.map.MutableMap;

public class DfAggregate
extends IntrinsicFunctionDescriptor
{
    private static final MutableMap<String, Function<String, AggregateFunction>> AGG_FUNCTION_FACTORIES;

    static
    {
        AGG_FUNCTION_FACTORIES = Maps.mutable.of();
        AGG_FUNCTION_FACTORIES.put("Sum", AggregateFunction::sum);
        AGG_FUNCTION_FACTORIES.put("Avg", AggregateFunction::avg);
        AGG_FUNCTION_FACTORIES.put("Min", AggregateFunction::min);
        AGG_FUNCTION_FACTORIES.put("Max", AggregateFunction::max);
        AGG_FUNCTION_FACTORIES.put("Count", AggregateFunction::count);
        AGG_FUNCTION_FACTORIES.put("Same", AggregateFunction::same);
    }
    public DfAggregate()
    {
        super("aggregate");
    }

    @Override
    public Value evaluate(VectorValue parameters)
    {
        Value dataFrameParameter = parameters.get(0);
        this.assertParameterType(ValueType.DATA_FRAME, dataFrameParameter.getType());

        Value aggColListParameter = parameters.get(1);
        this.assertParameterType(ValueType.VECTOR, aggColListParameter.getType());

        Value aggFunctionParameter = parameters.get(2);
        this.assertParameterType(ValueType.VECTOR, aggFunctionParameter.getType());

        DataFrame df = ((DataFrameValue) dataFrameParameter).dataFrameValue();
        ListIterable<String> aggregationColumnNames = ((VectorValue) aggColListParameter).getElements().collect(Value::stringValue);
        ListIterable<String> aggregationFunctionNames = ((VectorValue) aggFunctionParameter).getElements().collect(Value::stringValue);

        ListIterable<AggregateFunction> aggregators =
                aggregationColumnNames.collectWithIndex((col, index) -> AGG_FUNCTION_FACTORIES.get(aggregationFunctionNames.get(index)).apply(col));

        DataFrame aggregated;

        if (parameters.size() > 3)
        {
            Value aggByColListParameter = parameters.get(3);
            ListIterable<String> aggregateByColumNames;
            if (aggByColListParameter.isString()) // work around the parsing bug
            {
                aggregateByColumNames = Lists.immutable.of(aggByColListParameter.stringValue());
            }
            else
            {
                this.assertParameterType(ValueType.VECTOR, aggByColListParameter.getType());
                aggregateByColumNames = ((VectorValue) aggByColListParameter).getElements().collect(Value::stringValue);
            }

            aggregated = df.aggregateBy(aggregators, aggregateByColumNames);
        }
        else
        {
            aggregated = df.aggregate(aggregators);
        }
        return new DataFrameValue(aggregated);
    }

    @Override
    public String usageString()
    {
        return "Usage: " + this.getName() + "(df, ('aggCol1', ...), ('function', ...)[, ('aggBy', ...)])";
    }

    @Override
    public ValueType returnType(ListIterable<ValueType> parameterTypes)
    {
        return ValueType.DATA_FRAME;
    }
}
