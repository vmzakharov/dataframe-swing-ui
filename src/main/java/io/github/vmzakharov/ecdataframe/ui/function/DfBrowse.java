package io.github.vmzakharov.ecdataframe.ui.function;

import io.github.vmzakharov.ecdataframe.dsl.EvalContext;
import io.github.vmzakharov.ecdataframe.dsl.function.IntrinsicFunctionDescriptor;
import io.github.vmzakharov.ecdataframe.dsl.value.DataFrameValue;
import io.github.vmzakharov.ecdataframe.dsl.value.Value;
import io.github.vmzakharov.ecdataframe.dsl.value.ValueType;
import io.github.vmzakharov.ecdataframe.ui.FloatingBrowseWindow;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ListIterable;

public class DfBrowse
extends IntrinsicFunctionDescriptor
{
    public DfBrowse()
    {
        super("browse", Lists.immutable.of("dataFrame"));
    }

    @Override
    public Value evaluate(EvalContext context)
    {
        Value parameter = context.getVariable("dataFrame");

        this.assertParameterType(ValueType.DATA_FRAME, parameter.getType());

        var browseWindow = new FloatingBrowseWindow(((DataFrameValue) parameter).dataFrameValue());
        browseWindow.setVisible(true);

        return Value.VOID;
    }

    @Override
    public ValueType returnType(ListIterable<ValueType> parameterTypes)
    {
        return ValueType.VOID;
    }
}
