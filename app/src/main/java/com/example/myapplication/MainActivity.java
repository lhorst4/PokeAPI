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
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.KeyPairGenerator;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            char[] invalid_chars = "%&*(@)!;:<>".toCharArray();
            String pokemon = searchET.getText().toString().toLowerCase();
            for(int i = 0; i < invalid_chars.length; i++){
                String check = String.valueOf(invalid_chars[i]);
                if(pokemon.contains(check)){
                    Toast.makeText(getApplicationContext(), "Invalid Character in Search Bar.", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            try{
                int dexNum = Integer.parseInt(searchET.getText().toString());
                if(dexNum > 1017 || dexNum < 1){
                    Toast.makeText(getApplicationContext(), "Search using a number between 1 and 1017", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.i("SearchType", "Number");
            }catch(Exception e){
                Log.i("SearchType", "Name");
            }
            makeRequest(pokemon);
        }
    };

    private void makeRequest(String pokemon) {
        ANRequest req = AndroidNetworking.get("https://pokeapi.co/api/v2/pokemon/"+pokemon)
                .setPriority(Priority.LOW).build();

        // Log.i("URL", "https://pokeapi.co/api/v2/pokemon/"+pokemon);
        req.getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject pokemons) {
                Log.i("Data Request", "Data received.");
                try {
                    String pokename = pokemons.getString("name");
                    int pokedexnum = pokemons.getInt("id");
                    int pokeheight = pokemons.getInt("height");
                    int pokeweight = pokemons.getInt("weight");
                    int basexp;
                    try {
                        basexp = pokemons.getInt("base_experience");
                    }catch(Exception e){
                        basexp = 0;
                    }
                    JSONArray pokeabilities = pokemons.getJSONArray("abilities");
                    JSONObject abilities = pokeabilities.getJSONObject(0);
                    JSONObject abilityobj = abilities.getJSONObject("ability");
                    String ability = abilityobj.getString("name");
                    JSONArray movearr = pokemons.getJSONArray("moves");
                    JSONObject moveobj = movearr.getJSONObject(0);
                    JSONObject moveobj_2 = moveobj.getJSONObject("move");
                    String move = moveobj_2.getString("name");
                    nameTV.setText(pokename);
                    numberTV.setText(String.valueOf(pokedexnum));
                    weightTV.setText(String.valueOf(pokeweight));
                    heightTV.setText(String.valueOf(pokeheight));
                    xpTV.setText(String.valueOf(basexp));
                    abilityTV.setText(ability);
                    moveTV.setText(move);
                    setImage(pokedexnum);
                }catch(Exception e){
                    Log.i("Exception", "JSON error");
                }
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(getApplicationContext(), "Error on getting data ", Toast.LENGTH_LONG).show();
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
    public void setImage(int pokedexnum){
        String pokedexnumstr = "";
        if(pokedexnum < 10){
            pokedexnumstr = "00" + pokedexnum;
        }else if(pokedexnum < 100){
            pokedexnumstr = "0" + pokedexnum;
        }else{
            pokedexnumstr = String.valueOf(pokedexnum);
        }

        if(pokedexnum < 906) {
            String imageURL = "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/" + pokedexnumstr + ".png";
            Picasso.get().load(imageURL).into(pokeIV);
        }else{
            String imageURL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+pokedexnumstr+".png";
            Picasso.get().load(imageURL).into(pokeIV);
        }
    }
}