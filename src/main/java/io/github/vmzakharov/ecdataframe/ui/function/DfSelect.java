package io.github.vmzakharov.ecdataframe.ui.function;

import io.github.vmzakharov.ecdataframe.dataframe.DataFrame;
import io.github.vmzakharov.ecdataframe.dsl.EvalContext;
import io.github.vmzakharov.ecdataframe.dsl.function.IntrinsicFunctionDescriptor;
import io.github.vmzakharov.ecdataframe.dsl.value.DataFrameValue;
import io.github.vmzakharov.ecdataframe.dsl.value.Value;
import io.github.vmzakharov.ecdataframe.dsl.value.ValueType;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ListIterable;

public class DfSelect
extends IntrinsicFunctionDescriptor
{
    public DfSelect()
    {
        super("select", Lists.immutable.of("dataFrame", "filterExpression"));
    }

    @Override
    public Value evaluate(EvalContext context)
    {
        Value dataFrameParameter = context.getVariable("dataFrame");
        Value filterExpressionParameter = context.getVariable("filterExpression");

        this.assertParameterType(ValueType.DATA_FRAME, dataFrameParameter.getType());
        this.assertParameterType(ValueType.STRING, filterExpressionParameter.getType());

        DataFrame df = ((DataFrameValue) dataFrameParameter).dataFrameValue();

        DataFrame selected = df.selectBy(filterExpressionParameter.stringValue());

        return new DataFrameValue(selected);
    }

    @Override
    public ValueType returnType(ListIterable<ValueType> parameterTypes)
    {
        return ValueType.DATA_FRAME;
    }
}
