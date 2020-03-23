package com.example.lab3_1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {

    public BigInteger Factoring(FermatsFactoring cFermatsFactoring, BigInteger n) {
        return cFermatsFactoring.factoring(n);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = (Button) findViewById(R.id.button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.editText);
                TextView testTextView = (TextView) findViewById(R.id.testTextView);

                int num = Integer.parseInt(editText.getText().toString());

                BigInteger result = Factoring(new FermatsFactoring(), BigInteger.valueOf(num));

                if (Integer.valueOf(result.toString()) == 2) {
                    testTextView.setText("Парне число");
                } else if (Integer.valueOf(result.toString()) == num) {
                    testTextView.setText(Math.round(Math.sqrt(Double.valueOf(String.valueOf(num)))) + " ^ 2");
                } else {
                    testTextView.setText(result + " * " + num/Integer.valueOf(result.toString()));
                }
            }
        });
    }
}
