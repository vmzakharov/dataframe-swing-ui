package io.github.vmzakharov.ecdataframe.ui.function;

import io.github.vmzakharov.ecdataframe.dataset.CsvDataSet;
import io.github.vmzakharov.ecdataframe.dsl.EvalContext;
import io.github.vmzakharov.ecdataframe.dsl.function.IntrinsicFunctionDescriptor;
import io.github.vmzakharov.ecdataframe.dsl.value.DataFrameValue;
import io.github.vmzakharov.ecdataframe.dsl.value.Value;
import io.github.vmzakharov.ecdataframe.dsl.value.ValueType;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ListIterable;

import java.nio.file.Paths;

public class DfLoad
extends IntrinsicFunctionDescriptor
{
    public DfLoad()
    {
        super("load", Lists.immutable.of("path"));
    }

    @Override
    public Value evaluate(EvalContext context)
    {
        Value parameter = context.getVariable("path");

        this.assertParameterType(ValueType.STRING, parameter.getType());

        CsvDataSet dataSet = new CsvDataSet(Paths.get(parameter.stringValue()), "DataFrame");

        return new DataFrameValue(dataSet.loadAsDataFrame());
    }

    @Override
    public ValueType returnType(ListIterable<ValueType> parameterTypes)
    {
        return ValueType.DATA_FRAME;
    }
}
