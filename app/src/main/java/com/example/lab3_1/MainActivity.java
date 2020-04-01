package com.example.lab3_1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {

    public BigInteger Factoring(FermatsFactoring cFermatsFactoring, BigInteger n) throws InterruptedException {
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

                final int num = Integer.parseInt(editText.getText().toString());

                ExecutorService executor = Executors.newCachedThreadPool();
                Callable<Object> task = new Callable<Object>() {
                    public Object call() throws InterruptedException {
                        return Factoring(new FermatsFactoring(), BigInteger.valueOf(num));
                    }
                };
                Future<Object> future = executor.submit(task);

                try {
                    Object result = future.get(3, TimeUnit.SECONDS);
                    if (Integer.valueOf(result.toString()) == 2) {
                        testTextView.setText("Парне число");
                    } else if (Integer.valueOf(result.toString()) == num) {
                        testTextView.setText(Math.round(Math.sqrt(Double.valueOf(String.valueOf(num)))) + " ^ 2");
                    } else {
                        testTextView.setText(result + " * " + num/Integer.valueOf(result.toString()));
                    }
                } catch (TimeoutException ex) {
                    testTextView.setText("Час вичерпаний");
                } catch (InterruptedException e) {
                    testTextView.setText("Щось пішло не так");
                } catch (ExecutionException e) {
                    testTextView.setText("Щось пішло не так");
                } finally {
                    future.cancel(true);
                }
            }
        });
    }
}
