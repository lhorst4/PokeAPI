package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.squareup.picasso.Picasso;

import java.security.KeyPairGenerator;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            char[] invalid_chars = "%&*(@)!;:<>".toCharArray();
            String pokemon = searchET.getText().toString();
            for(int i = 0; i < invalid_chars.length; i++){
                String check = String.valueOf(invalid_chars[i]);
                if(pokemon.contains(check)){
                    Toast.makeText(getApplicationContext(), "Invalid Character in Search Bar.", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            try{
                int dexNum = Integer.parseInt(searchET.getText().toString());
                if(dexNum >= 1010 || dexNum < 1){
                    Toast.makeText(getApplicationContext(), "Search using a number between 1 and 1010", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.i("SearchType", "Number");
            }catch(Exception e){
                Log.i("SearchType", "Name");
            }
            String imageURL = "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/001.png";
            Picasso.get().load(imageURL).into(pokeIV);
            makeRequest(pokemon);
        }
    };

    private void makeRequest(String pokemon){
        ANRequest req = AndroidNetworking.get("https://pokeapi.co/api/v2/pokemon/{pokemon}")
                .addPathParameter(searchET.getText().toString().toLowerCase(), pokemon)
                .setPriority(Priority.LOW)
                .build();
        // Log.i("URL", "https://pokeapi.co/api/v2/pokemon/"+pokemon);
        req.getAsObjectList(Pokemon.class, new ParsedRequestListener<List<Pokemon>>(){
            @Override
            public void onResponse(List<Pokemon> pokemons) {
                String TAG = "POKEMON";
                Log.i(TAG, "userList size : " + pokemons.size());
                for(Pokemon pokemon: pokemons){

                }
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(getApplicationContext(),"Error on getting data ", Toast.LENGTH_LONG).show();
            }
        });
    }

    Button searchBT;
    EditText searchET;
    ImageView pokeIV;
    TextView nameTV;
    TextView numberTV;
    TextView weightTV;
    TextView heightTV;
    TextView xpTV;
    TextView moveTV;
    TextView abilityTV;
    LinkedList<Pokemon> pokelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidNetworking.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        if(pokelist == null) {
            pokelist = new LinkedList<>();
        }

        searchBT = findViewById(R.id.searchBT);
        searchET = findViewById(R.id.searchET);
        pokeIV = findViewById(R.id.pokemonIV);
        nameTV = findViewById(R.id.nameTV);
        numberTV = findViewById(R.id.numberTV);
        weightTV = findViewById(R.id.weightTV);
        heightTV = findViewById(R.id.heightTV);
        xpTV = findViewById(R.id.xpTV);
        moveTV = findViewById(R.id.moveTV);
        abilityTV = findViewById(R.id.abilityTV);

        searchBT.setOnClickListener(searchListener);

    }
}