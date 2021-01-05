package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // widget members:
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    // keys for landscape orientation change
    private static final String STATE_PENDING_OPERATION = "PendingOperation";
    private static final String STATE_OPERAND1 = "Operand1";

    // variables to hold the operands and type of calculations
    private Double operand1 = null;
    private String pendingOperation = "-";

    // public members:
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button buttonDot;
    Button buttonPlus;
    Button buttonMultiply;
    Button buttonMinus;
    Button buttonDivide;
    Button buttonEquals;
    Button buttonNegative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    // onSaveInstanceState:
    // To save the value of the operation when the orientation is changed:
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        // add the values to the state before it gets processed:
        outState.putString(STATE_PENDING_OPERATION, pendingOperation);

        if(operand1 != null)
            outState.putDouble(STATE_OPERAND1, operand1);

        super.onSaveInstanceState(outState);
    }

    // onRestoreInstanceState:
    // To retrieve and update the value of the operation when the orientation is changed:
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // retrieve the values:
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);

        // update the operaton:
        displayOperation.setText(pendingOperation);
    }

    public void initialize() {

        // initialization + set on click listener
        result = findViewById(R.id.result);
        newNumber = findViewById(R.id.newNumber_ET);
        displayOperation = findViewById(R.id.operation);

        // assign 16 buttons: operations
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        buttonDot = findViewById(R.id.buttonDot);

        buttonEquals = findViewById(R.id.buttonEquals);
        buttonDivide = findViewById(R.id.buttonDivide);
        buttonMultiply = findViewById(R.id.buttonMultiply);
        buttonPlus = findViewById(R.id.buttonPlus);
        buttonMinus = findViewById(R.id.buttonMinus);
        buttonNegative = findViewById(R.id.buttonNegative);

        // create button listener:
        createButtonListener();


        // create operation listener:
        createOperationLister();

        // create negative listener:
        createNegativeListener();

    } // ends initialize()

    private void createNegativeListener() {

       buttonNegative.setOnClickListener(v -> {
           String value = newNumber.getText().toString();

           if(value.length() == 0){
               newNumber.setText("-");
           } else {

               try{
                   Double doubleValue = Double.valueOf(value);
                   doubleValue *= -1; // make it a negative number
                   newNumber.setText(doubleValue.toString());
               } catch (NumberFormatException e){

                   // newNumber was "-_ or ".", so clear it
                   newNumber.setText("");
               }
           }
       });
    }

    // create button listener:
    public void createButtonListener(){

        View.OnClickListener listener = v -> {

            Button b = (Button)v;
            newNumber.append(b.getText().toString());
        };

        // assign the listener to each button:
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);
    }

    // create operation listener:
    public void createOperationLister(){
        View.OnClickListener operationListener = v -> {

            Button b = (Button)v;

            String operation = b.getText().toString();
            String value = newNumber.getText().toString();

            try{
                Double doubleValue = Double.valueOf(value);
                performOperation(doubleValue,operation);
            } catch (NumberFormatException e){
                newNumber.setText("");
            }

            pendingOperation = operation;
            displayOperation.setText(pendingOperation);
            newNumber.setText("");
        };

        buttonEquals.setOnClickListener(operationListener);
        buttonMinus.setOnClickListener(operationListener);
        buttonPlus.setOnClickListener(operationListener);
        buttonMultiply.setOnClickListener(operationListener);
        buttonDivide.setOnClickListener(operationListener);
    }

    public void performOperation(Double value, String operation){

        // check if operand1 is null
        // if null there isn't any calculation to perform but we have to store the value in the operand
        if(null == operand1){

            // store the value in the operand:
            operand1 = value;

            // update the value on the string:
        } else {

            // if operand1 isnt null the value passed in must be operand 2

            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
            }

            switch (pendingOperation){
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if(value == 0){
                        operand1 = 0.0;
                    } else {
                        operand1 /=value;
                    }
                    break;
                case "*":
                    operand1 *= value;
                     break;
                case "-":
                    operand1 -= value;
                    break;
                case "+":
                    operand1 += value;
                    break;
            } // ends switch

            // display the result
            // reset the number
            result.setText(operand1.toString());
            newNumber.setText("");
        } // ends else

    } // ends performOperation
}


