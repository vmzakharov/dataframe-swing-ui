package io.github.vmzakharov.ecdataframe.ui.function;

import io.github.vmzakharov.ecdataframe.dataframe.DataFrame;
import io.github.vmzakharov.ecdataframe.dsl.EvalContext;
import io.github.vmzakharov.ecdataframe.dsl.Expression;
import io.github.vmzakharov.ecdataframe.dsl.SimpleEvalContext;
import io.github.vmzakharov.ecdataframe.dsl.function.BuiltInFunctions;
import io.github.vmzakharov.ecdataframe.dsl.value.DataFrameValue;
import io.github.vmzakharov.ecdataframe.dsl.value.Value;
import io.github.vmzakharov.ecdataframe.dsl.value.ValueType;
import io.github.vmzakharov.ecdataframe.dsl.visitor.InMemoryEvaluationVisitor;
import io.github.vmzakharov.ecdataframe.ui.DataFrameUtil;
import io.github.vmzakharov.ecdataframe.util.ExpressionParserHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AggregateFunctionTest
{
    private DataFrame dataFrame;
    @BeforeEach
    public void setupDataFrame()
    {
        this.dataFrame = new DataFrame("DF")
                .addStringColumn("Name").addLongColumn("Foo").addDoubleColumn("Bar").addStringColumn("Baz")
                .addRow("Alice",  1, 10.1, "A")
                .addRow("Alice", 11, 10.1, "A")
                .addRow("Bob",    2, 10.1, "B")
                .addRow("Carl",  23, 20.2, "C")
                .addRow("Carl",  24, 20.2, "X")
                ;
    }

    @Test
    public void aggregate()
    {
        BuiltInFunctions.addFunctionDescriptor(new DfAggregate());

        SimpleEvalContext context = new SimpleEvalContext();
        context.setVariable("df", new DataFrameValue(dataFrame));

        DataFrame aggregated = this.evaluateToDataFrame("aggregate(df, ('Foo','Bar','Baz'), ('Sum','Sum','Same'))", context);

        DataFrameUtil.assertEquals(
                new DataFrame("Aggregated")
                        .addLongColumn("Foo").addDoubleColumn("Bar").addStringColumn("Baz")
                        .addRow(61, 70.7, null)
                , aggregated
        );
        System.out.println(aggregated.asCsvString());
    }

    @Test
    public void aggregateBy()
    {
        BuiltInFunctions.addFunctionDescriptor(new DfAggregate());

        SimpleEvalContext context = new SimpleEvalContext();
        context.setVariable("df", new DataFrameValue(dataFrame));

        DataFrame aggregated = this.evaluateToDataFrame(
            """
            aggregate(df, ('Foo','Bar','Baz'), ('Sum','Sum','Same'), ('Name'))
            """, context);

        System.out.println(aggregated.asCsvString());
    }

    private DataFrame evaluateToDataFrame(String expressionString, EvalContext context)
    {
        Expression expression = ExpressionParserHelper.DEFAULT.toScript(expressionString);
        InMemoryEvaluationVisitor visitor = new InMemoryEvaluationVisitor(context);
        Value result = expression.evaluate(visitor);

        assertEquals(ValueType.DATA_FRAME, result.getType());
        return ((DataFrameValue) result).dataFrameValue();
    }
}
