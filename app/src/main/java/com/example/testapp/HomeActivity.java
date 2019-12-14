package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gridLayout=findViewById(R.id.homegrid);


        setSingleEvent(gridLayout);


    }

    private void setSingleEvent(GridLayout gridLayout) {

        for(int i=0;i<gridLayout.getChildCount();i++){

            CardView cardView= (CardView) gridLayout.getChildAt(i);
            final int finalI1 = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(finalI1 ==0)
                    {
                        Intent intent=new Intent(HomeActivity.this,AddPurchasesActivity.class);
                        startActivity(intent);
                    }
                    else  if(finalI1 ==1)
                    {
                        Intent intent=new Intent(HomeActivity.this,AddSalesActivity.class);
                        startActivity(intent);
                    }
                    else  if(finalI1 ==2)
                    {
                        Intent intent=new Intent(HomeActivity.this,ProductListActivity.class);
                        startActivity(intent);
                    }
                    else  if(finalI1 ==3)
                    {
                        Intent intent=new Intent(HomeActivity.this,StockActivity.class);
                        startActivity(intent);
                    }
                    else  if(finalI1 ==4)
                    {
                        Intent intent=new Intent(HomeActivity.this,VendorDetailsActivity.class);
                        startActivity(intent);
                    }
                    else  if(finalI1 ==5)
                    {
                        Intent intent=new Intent(HomeActivity.this,CustomerDetailsActivity.class);
                        startActivity(intent);
                    }
                    else  if(finalI1 ==6)
                    {
                        Intent intent=new Intent(HomeActivity.this,OrdersActivity.class);
                        startActivity(intent);
                    }
                    else  if(finalI1 ==7)
                    {
                        Intent intent=new Intent(HomeActivity.this,ProfitLossActivity.class);
                        startActivity(intent);
                    }
                    else  if(finalI1 ==8)
                    {
                        Intent intent=new Intent(HomeActivity.this,NotesActivity.class);
                        startActivity(intent);
                    }
                    else  if(finalI1 ==9)
                    {
                        Intent intent=new Intent(HomeActivity.this,SupportActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(HomeActivity.this,"Please Choose A Valid Option",Toast.LENGTH_SHORT).show();

                    }







                }
            });
        }


    }

}
