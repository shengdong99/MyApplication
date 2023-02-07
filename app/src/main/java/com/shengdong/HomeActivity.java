package com.shengdong;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.shengdong.recyclerviewtest.utils.adapter.RopaItemAdapter;
import com.shengdong.recyclerviewtest.utils.model.ShoeCart;
import com.shengdong.recyclerviewtest.utils.model.ShoeItem;
import com.shengdong.recyclerviewtest.viewmodel.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements RopaItemAdapter.ShoeClickedListeners {

    private FirebaseAuth mAuth;
    private RopaItemAdapter adapter;
    private List<ShoeItem> shoeItemList;
    private List<ShoeCart> shoeCartList;
    private CartViewModel viewModel;
    private CoordinatorLayout coordinatorLayout;
    //private ImageView cartImageView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    //ListView listView;
    RecyclerView recyclerView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar tb = (Toolbar)findViewById(R.id.toolbar);
        tb.setTitle("Home");
        tb.setLogo(R.drawable.ic_baseline_home);
        setSupportActionBar(tb);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId())
                {
                    case R.id.nav_home:
                    {
                        intent = new Intent(HomeActivity.this, HomeActivity.class);
                        startActivity(intent);
                        Toast.makeText(HomeActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.nav_category:
                    {
                        intent = new Intent(HomeActivity.this, CategoryProduct.class);
                        startActivity(intent);
                        Toast.makeText(HomeActivity.this, "Gallery Selected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.cesta:
                    {
                        intent = new Intent(HomeActivity.this, CartActivity.class);
                        startActivity(intent);
                        Toast.makeText(HomeActivity.this, "Cesta Selected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.contactos:
                    {

                        Toast.makeText(HomeActivity.this, "Contactos Selected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.about:
                    {
                        Toast.makeText(HomeActivity.this, "About Selected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.location:
                    {
                        Toast.makeText(HomeActivity.this, "Location Selected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                return false;
            }
        });

        initializeVariables();
        setUpList();

        mAuth = FirebaseAuth.getInstance();

        adapter.setShoeItemList(shoeItemList);
        recyclerView.setAdapter(adapter);

       /* cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.getAllCartItems().observe(this, new Observer<List<ShoeCart>>() {
            @Override
            public void onChanged(List<ShoeCart> shoeCarts) {
                shoeCartList.addAll(shoeCarts);
            }
        });

    }
         /*   ItemList[] mydata = new ItemList[]{
            new ItemList("Camiseta hombre", R.drawable.camisa1),
            new ItemList("Ropa para mujer", R.drawable.mujer),
            new ItemList("Zapatillas", R.drawable.sapatilla),
            new ItemList("Calcetines", R.drawable.calcetines),
            new ItemList("Bufandas", R.drawable.bufanda),
            new ItemList("Camiseta deporte", R.drawable.camiseta5)

    };*/
        private void setUpList() {
            shoeItemList.add(new ShoeItem("Nike Revolution", "Camiseta hombre", R.drawable.camisa1, 15));
            shoeItemList.add(new ShoeItem("Nike Flex Run 2021", "Ropa para mujer", R.drawable.mujer, 20));
            shoeItemList.add(new ShoeItem("Court Zoom Vapor", "Zapatillas", R.drawable.sapatilla, 18));
            shoeItemList.add(new ShoeItem("EQ21 Run COLD.RDY", "Calcetines", R.drawable.calcetines, 16.5));
            shoeItemList.add(new ShoeItem("Adidas Ozelia", "Bufandas", R.drawable.bufanda, 20));
            shoeItemList.add(new ShoeItem("Adidas Questar", "Camiseta deporte", R.drawable.camiseta5, 22));
            shoeItemList.add(new ShoeItem("Adidas Questar", "ADIDAS", R.drawable.adidas_questar_shoes, 12));
            shoeItemList.add(new ShoeItem("Adidas Ultraboost", "ADIDAS", R.drawable.adidas_ultraboost, 15));

        }

        private void initializeVariables() {

            //cartImageView = findViewById(R.id.cartIv);
            coordinatorLayout = findViewById(R.id.coordinator_Layout);
            shoeCartList = new ArrayList<>();
            viewModel = new ViewModelProvider(this).get(CartViewModel.class);
            shoeItemList = new ArrayList<>();
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter = new RopaItemAdapter(this);

        }


/*    recyclerView=findViewById(R.id.recyclerView);
    ItemAdapter adapter= new ItemAdapter(mydata);
        recyclerView.setHasFixedSize(true);
    LinearLayoutManager linear = new LinearLayoutManager(getApplicationContext());
        linear.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);*/

    public void onCardClicked(ShoeItem shoe) {
        Intent intent = new Intent(HomeActivity.this, DetailedActivity.class);
        intent.putExtra("shoeItem", shoe);
        startActivity(intent);
    }

    @Override
    public void onAddToCartBtnClicked(ShoeItem shoeItem) {
        ShoeCart shoeCart = new ShoeCart();
        shoeCart.setShoeName(shoeItem.getShoeName());
        shoeCart.setShoeBrandName(shoeItem.getShoeBrandName());
        shoeCart.setShoePrice(shoeItem.getShoePrice());
        shoeCart.setShoeImage(shoeItem.getShoeImage());

        final int[] quantity = {1};
        final int[] id = new int[1];

        if (!shoeCartList.isEmpty()) {
            for (int i = 0; i < shoeCartList.size(); i++) {
                if (shoeCart.getShoeName().equals(shoeCartList.get(i).getShoeName())) {
                    quantity[0] = shoeCartList.get(i).getQuantity();
                    quantity[0]++;
                    id[0] = shoeCartList.get(i).getId();
                }
            }
        }

        Log.d("TAG", "onAddToCartBtnClicked: " + quantity[0]);

        if (quantity[0] == 1) {
            shoeCart.setQuantity(quantity[0]);
            shoeCart.setTotalItemPrice(quantity[0] * shoeCart.getShoePrice());
            viewModel.insertCartItem(shoeCart);
        } else {
            viewModel.updateQuantity(id[0], quantity[0]);
            viewModel.updatePrice(id[0], quantity[0] * shoeCart.getShoePrice());
        }

        makeSnackBar("Item Added To Cart");
    }

    private void makeSnackBar(String msg) {
        Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_SHORT)
                .setAction("Go to Cart", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(HomeActivity.this, CartActivity.class));
                    }
                }).show();

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        int item_id = item.getItemId();

        if(item_id==R.id.profile){
            Toast.makeText(this, "This is profile item", Toast.LENGTH_SHORT).show();
        }
        else if(item_id==R.id.contactos){
            Toast.makeText(this, "This is contactos item", Toast.LENGTH_SHORT).show();
        }else if(item_id==R.id.setting){
            Toast.makeText(this, "This is setting item", Toast.LENGTH_SHORT).show();
        }
        else if(item_id==R.id.exit){
            mAuth.signOut();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }else if(drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // FirebaseRecyclerOptions<>
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }
}




       /* listView=findViewById(R.id.listview);

        Adaptador adaptador= new Adaptador(HomeActivity.this,ropatitles,imageid);
        listView.setAdapter(adaptador);*/







