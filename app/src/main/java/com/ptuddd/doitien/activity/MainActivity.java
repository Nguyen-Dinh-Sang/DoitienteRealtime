package com.ptuddd.doitien.activity;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ptuddd.doitien.R;
import com.ptuddd.doitien.model.CurrencyModel;
import com.ptuddd.doitien.server.RssCurrencyManager;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    public static String TAG ="nhatnhat";
    private ConstraintLayout parent;
    private Spinner unit1,unit2;
    private EditText edt1,edt2;
    private TextView tvcongthuc;
    private Button btndot,btndel,btntry;
    private ArrayAdapter aa;
    private int currentSelectUnit1=0;
    private int currentSelectUnit2=0;
    private List<CurrencyModel> currencyModels;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        loadData();

    }

    private void loadData() {
        showDialogLoading("Vui lòng đợi ứng dụng lấy dữ liệu từ server",false);
        RssCurrencyManager.getInstance().getCurrencysFromRss("https://usd.fxexchangerate.com/rss.xml", new RssCurrencyManager.RssCurrencyManagerListener() {
            @Override
            public void onGetCurrencyFromRssSuccess(final List<CurrencyModel> currencys) {
                currencyModels =currencys;
                currencyModels.add(0,new CurrencyModel("United States Dollar",1,"USD"));
                cancleDialogLoading();
                parent.setVisibility(View.VISIBLE);
                aa = new ArrayAdapter(MainActivity.this,android.R.layout.simple_spinner_item, currencyModels);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                unit1.setAdapter(aa);
                unit2.setAdapter(aa);
            }

            @Override
            public void onGetCurrencyFromRssFail(String error) {
                cancleDialogLoading();
                btntry.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Đã xảy ra lỗi , vui lòng kiểm tra kết nối mạng và thử lại", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onGetCurrencyFromRssFail: " + error);

            }
        });
    }

    private void exchangeCurrencies(EditText editTextSetValues,EditText editTextGetValues,CurrencyModel selectedCurrencyGet,CurrencyModel selectedCurrencySet){
        if(selectedCurrencyGet!=null && selectedCurrencySet!=null) {
            double currentRateGet = selectedCurrencyGet.getRate();
            double currentRateSet = selectedCurrencySet.getRate();
            BigDecimal newValue = new BigDecimal(Float.parseFloat(editTextGetValues.getText().toString()) / currentRateGet * currentRateSet, MathContext.DECIMAL64);
            tvcongthuc.setText(editTextGetValues.getText().toString() + ""+selectedCurrencyGet.getName()+" = "+newValue+" "+selectedCurrencySet.getName());
            editTextSetValues.setText(newValue + "");
        }

    }

    private void initView() {
        unit1 = findViewById(R.id.sp_unit1);
        unit2 = findViewById(R.id.sp_unit2);
        edt1 = findViewById(R.id.edt_unit1);
        edt2= findViewById(R.id.edt_unit2);
        parent = findViewById(R.id.cl_parent);
        btndot= findViewById(R.id.btndot);
        btndot.setOnClickListener(this);
        btndel= findViewById(R.id.btndel);
        btndel.setOnClickListener(this);
        btntry=findViewById(R.id.btn_tryagain);
        btntry.setOnClickListener(this);
        tvcongthuc = findViewById(R.id.tv_congthuc);
        unit1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentSelectUnit1=position;
                if(edt1.hasFocus()){
                    exchangeCurrencies(edt2,edt1,currencyModels.get(currentSelectUnit1),currencyModels.get(currentSelectUnit2));
                }else {
                    exchangeCurrencies(edt1,edt2,currencyModels.get(currentSelectUnit2),currencyModels.get(currentSelectUnit1));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        unit2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentSelectUnit2=position;
                if(edt1.hasFocus()){
                    exchangeCurrencies(edt2,edt1,currencyModels.get(currentSelectUnit1),currencyModels.get(currentSelectUnit2));
                }else {
                    exchangeCurrencies(edt1,edt2,currencyModels.get(currentSelectUnit2),currencyModels.get(currentSelectUnit1));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final TextWatcher tw1 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                if(isNumeric(edt1.getText().toString())||edt1.getText().toString().equals("-")||edt1.getText().toString().equals(".")){
//                }else {
//                    edt1.setText(edt1.getText());
//                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edt1.getText().toString().equals("")||edt1.getText().toString().equals("-")){
                    edt1.setText("0.0");
                }else {
                    exchangeCurrencies(edt2,edt1,currencyModels.get(currentSelectUnit1),currencyModels.get(currentSelectUnit2));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        final TextWatcher tw2 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edt2.getText().toString().equals("")||edt2.getText().toString().equals("-")){
                    edt2.setText("0.0");
                }else {
                    exchangeCurrencies(edt1,edt2,currencyModels.get(currentSelectUnit2),currencyModels.get(currentSelectUnit1));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        edt1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    edt1.addTextChangedListener(tw1);
                }else {
                    edt1.removeTextChangedListener(tw1);
                }
            }
        });
        edt2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    edt2.addTextChangedListener(tw2);
                }else {
                    edt2.removeTextChangedListener(tw2);
                }
            }
        });

    }

    private void addText(String string){
        if(edt1.hasFocus()){
            if(string=="-"){
                if(edt1.getText().toString().charAt(0)=='-'){
                    edt1.setText(edt1.getText().toString().substring(1));
                }else {
                    edt1.setText("-"+edt1.getText());
                }
            }
            if(string=="del"){
                edt1.setText(edt1.getText().toString().substring(0,edt1.getText().length()-1));
            }
            if(string=="."){
                if (!edt1.getText().toString().contains("."))
                    edt1.setText(edt1.getText().toString()+".");
            }
            edt1.setSelection(edt1.getText().length());

        }
        if(edt2.hasFocus()){
            if(string=="-"){
                if(edt2.getText().toString().charAt(0)=='-'){

                    edt2.setText(edt2.getText().toString().substring(1));
                }else {

                    edt2.setText("-"+edt2.getText());
                }
            }
            if(string=="del"){
                edt2.setText(edt2.getText().toString().substring(0,edt2.getText().length()-1));
            }
            if(string=="."){
                if (!edt2.getText().toString().contains("."))
                    edt2.setText(edt2.getText().toString()+".");
            }
            edt2.setSelection(edt2.getText().length());
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btndot :{
                addText(".");
                break;
            }
            case R.id.btndel :{
                addText("del");
                break;
            }
            case R.id.btn_tryagain:{
                btntry.setVisibility(View.GONE);
                loadData();
                break;
            }
        }
    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        RssCurrencyManager.getInstance().getCurrencysFromRss("https://usd.fxexchangerate.com/rss.xml", new RssCurrencyManager.RssCurrencyManagerListener() {
//            @Override
//            public void onGetCurrencyFromRssSuccess(List<CurrencyModel> currencyModels) {
//                for (CurrencyModel c :
//                        currencyModels) {
//                    Log.d(TAG, c.getName() + " : " + c.getRate());
//                }
//            }
//
//            @Override
//            public void onGetCurrencyFromRssFail(String error) {
//                Log.d(TAG, "onGetCurrencyFromRssFail: " + error);
//
//            }
//        });
//
//    }
}
