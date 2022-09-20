package io.github.vmzakharov.ecdataframe.ui.function;

import io.github.vmzakharov.ecdataframe.dsl.function.IntrinsicFunctionDescriptor;
import io.github.vmzakharov.ecdataframe.dsl.value.ValueType;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ListIterable;

public class DfSave
extends IntrinsicFunctionDescriptor
{
    public DfSave()
    {
        super("save", Lists.immutable.of("dataFrame", "path"));
    }

    @Override
    public ValueType returnType(ListIterable<ValueType> parameterTypes)
    {
        return ValueType.VOID;
    }
}
