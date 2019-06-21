package com.tantuni.zamazon.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tantuni.zamazon.R;
import com.tantuni.zamazon.controllers.CustomerController;
import com.tantuni.zamazon.controllers.OrderController;
import com.tantuni.zamazon.controllers.adapters.CartProductAdapter;
import com.tantuni.zamazon.controllers.adapters.CheckoutProductAdapter;
import com.tantuni.zamazon.models.Address;
import com.tantuni.zamazon.models.Cart;
import com.tantuni.zamazon.models.CreditCard;
import com.tantuni.zamazon.models.Customer;
import com.tantuni.zamazon.models.Order;
import com.tantuni.zamazon.models.Product;
import com.tantuni.zamazon.models.User;
import com.tantuni.zamazon.networks.ProductCallback;
import com.tantuni.zamazon.networks.SharedPrefManager;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView textViewCartTotalPayment, textViewCartTotalPrice, textViewCartTotalTax;
    ProgressBar progressBarCheckout;
    RecyclerView recyclerViewCheckoutProducts;
    CheckoutProductAdapter checkoutProductAdapter;
    OrderController orderController;
    CustomerController customerController;
    Spinner spinnerCreditCards, spinnerAddresses;
    ArrayList<CreditCard> userCreditCards;
    ArrayList<Address> userAddresses;
    Cart userCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        spinnerCreditCards = (Spinner) findViewById(R.id.spinnerCreditCards);
        spinnerAddresses = (Spinner) findViewById(R.id.spinnerAddresses);

        spinnerCreditCards.setOnItemSelectedListener(this);
        spinnerAddresses.setOnItemSelectedListener(this);

        CustomerController.getUserCreditCardsById(getApplicationContext(), SharedPrefManager.getInstance(getApplicationContext()).getUser().getId(), new ProductCallback<ArrayList<CreditCard>>() {
            @Override
            public void onSuccess(ArrayList<CreditCard> creditCards, String message) {
                userCreditCards = creditCards;
                System.out.println(creditCards);
                ArrayList<String> creditCardItems = new ArrayList<>();
                for (CreditCard creditCard : creditCards) {
                    creditCardItems.add(creditCard.getNameOnCard() + " - " + creditCard.getCardNumber().substring(0, 4) + " **** **** " + creditCard.getCardNumber().substring(12, 16));
                }
                ArrayAdapter creditCardsAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, creditCardItems);
                creditCardsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCreditCards.setAdapter(creditCardsAdapter);
            }

            @Override
            public void onError(Exception exception) {
                Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        CustomerController.getUserAddressesById(getApplicationContext(), SharedPrefManager.getInstance(getApplicationContext()).getUser().getId(), new ProductCallback<ArrayList<Address>>() {
            @Override
            public void onSuccess(ArrayList<Address> addresses, String message) {
                userAddresses = addresses;
                ArrayList<String> addressItems = new ArrayList<>();
                for (Address address : addresses) {
                    addressItems.add(address.getTitle() + " - " + address.getAddress());
                }
                ArrayAdapter addressAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, addressItems);
                addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAddresses.setAdapter(addressAdapter);
            }

            @Override
            public void onError(Exception exception) {
                Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        textViewCartTotalPrice = (TextView) findViewById(R.id.textViewCartTotalPrice);
        textViewCartTotalTax = (TextView) findViewById(R.id.textViewCartTotalTax);
        textViewCartTotalPayment = (TextView) findViewById(R.id.textViewCartTotalPayment);

        recyclerViewCheckoutProducts = (RecyclerView) findViewById(R.id.recyclerViewCheckoutProducts);
        progressBarCheckout = (ProgressBar) findViewById(R.id.progressBarCheckout);

        customerController.getUserCartById(getApplicationContext(), SharedPrefManager.getInstance(getApplicationContext()).getUser().getId(), new ProductCallback<Cart>() {
            @Override
            public void onSuccess(Cart cart, String message) {
                userCart = cart;
                progressBarCheckout.setVisibility(View.GONE);
                setupRecycler(cart.getProducts());
                textViewCartTotalPrice.append(cart.getPayment().getTotal().toString() + " TL");
                textViewCartTotalTax.append(cart.getPayment().getTax().toString() + " TL");
                Double totalPayment = cart.getPayment().getTotal() + cart.getPayment().getTax();
                textViewCartTotalPayment.append(totalPayment.toString() + " TL");
            }

            @Override
            public void onError(Exception exception) {
                progressBarCheckout.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setupRecycler(List<Product> products) {
        checkoutProductAdapter = new CheckoutProductAdapter(getApplicationContext(), (ArrayList) products);
        recyclerViewCheckoutProducts.setAdapter(checkoutProductAdapter);
        recyclerViewCheckoutProducts.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
    }

    public void confirmPurchase(View view) throws JSONException {
        String customerId = SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
        CreditCard creditCard = userCreditCards.get(spinnerCreditCards.getSelectedItemPosition());
        Address address = userAddresses.get(spinnerAddresses.getSelectedItemPosition());
        Order order = new Order(customerId, userCart, creditCard, address);
        Log.d("order", order.toString());
        orderController.addOrder(getApplicationContext(), order, new ProductCallback<ArrayList<Order>>() {
            @Override
            public void onSuccess(ArrayList<Order> orders, String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Exception exception) {
                Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
