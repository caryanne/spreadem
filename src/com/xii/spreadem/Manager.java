package com.xii.spreadem;

import net.astesana.javaluator.DoubleEvaluator;
import net.astesana.javaluator.Function;
import net.astesana.javaluator.Operator;
import net.astesana.javaluator.Operator.Associativity;
import net.astesana.javaluator.Parameters;

import java.util.Iterator;
import java.util.Vector;

public class Manager {

    public Vector<Variable> Variables;
    private DoubleEvaluator Evaluator;
    private final Operator Ref = new Operator("#", 1, Associativity.RIGHT, 13);
    private final Function Eq = new Function("eq", 2);


    public Manager() {
        Parameters params = DoubleEvaluator.getDefaultParameters();
        params.add(Ref);
        params.add(Eq);
        Evaluator = new DoubleEvaluator(params) {
            @Override
            protected Double evaluate(Operator operator, Iterator<Double> arguments, Object evaluationContext) {
                if (operator == Ref)
                    return Variables.get(arguments.next().intValue()).getValue();
                else
                    return super.evaluate(operator, arguments, evaluationContext);
            }

            @Override
            protected Double evaluate(Function function, Iterator<Double> arguments, Object evaluationContext) {
                if (function == Eq)
                    return (arguments.next().doubleValue() == arguments.next().doubleValue()) ? 1.0 : 0.0;
                else
                    return super.evaluate(function, arguments, evaluationContext);
            }
        };
        Variables = new Vector<Variable>();
    }

    public int Add(String name, String function, String notify, int auto) {
        Variables.add(new Variable(name, function, 0, notify, auto));
        Update(Variables.size() - 1);
        return Variables.size() - 1;
    }

    public void Update() {
        for (Variable i : Variables) {
            if (i.getAuto() > 0 && (System.currentTimeMillis() - i.getLastUpdate() > i.getAuto()))
                Update(i);
        }

    }

    public void Update(Variable v) {
        v.setValue(Evaluator.evaluate(v.getFunction()));
        v.didUpdate();
        String notify = v.getValueNotify();
        if (!notify.equals(""))
            Update(notify);

    }

    public void Update(int id) {
        String notify = "";
        if (Variables.size() > id) {
            Variables.get(id).setValue(Evaluator.evaluate(Variables.get(id).getFunction()));
            Variables.get(id).didUpdate();
            notify = Variables.get(id).getValueNotify();
        } else
            System.out.printf("Variable %d does not exist\n", id);
        if (!notify.equals(""))
            Update(notify);

    }

    private void Update(String id) {
        String[] split = id.split(",");
        for (int i = 0; i < split.length; i++)
            Update(Integer.parseInt(split[i]));
    }

    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i < Variables.size(); i++) {
            ret = ret + (i + " " + Variables.get(i));
        }
        return ret;
    }

}
