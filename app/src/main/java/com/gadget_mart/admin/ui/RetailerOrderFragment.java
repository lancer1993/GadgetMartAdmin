package com.gadget_mart.admin.ui;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gadget_mart.admin.R;
import com.gadget_mart.admin.adapter.MainOrderAdapter;
import com.gadget_mart.admin.adapter.OrdersAdapter;
import com.gadget_mart.admin.callbacks.OrderCallBack;
import com.gadget_mart.admin.model.OrderModel;
import com.gadget_mart.admin.network.InternetConnectivity;
import com.gadget_mart.admin.service.OrderService;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RetailerOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RetailerOrderFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.order_recycler_view)
    RecyclerView order_recycler_view;
    @BindView(R.id.retailer_name_text)
    TextView retailer_name_text;
    @BindView(R.id.order_home_button)
    Button order_home_button;
    private ProgressDialog progressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int retailerId;
    private String retailerName;

    public RetailerOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RetailerOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RetailerOrderFragment newInstance(String param1, String param2) {
        RetailerOrderFragment fragment = new RetailerOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            retailerId = getArguments().getInt("RETAILER");
            retailerName = getArguments().getString("RETAILER_NAME");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retailer_order, container, false);;
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        retailer_name_text.setText(retailerName);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (InternetConnectivity.isConnectedToInternet(getContext())) {
            if(retailerId == 1 || retailerId == 2 || retailerId == 3){
                OrderService orderService = new OrderService();
                orderService.getOrdersForRetailer(getContext(), retailerId, new OrderCallBack() {
                    @Override
                    public void onOrderSuccess(List<OrderModel> list) {
                        progressDialog.dismiss();
                        order_recycler_view.setLayoutManager(mLayoutManager1);
                        order_recycler_view.setItemAnimator(new DefaultItemAnimator());
                        order_recycler_view.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                        order_recycler_view.setAdapter(new OrdersAdapter(getContext(), list, new OrdersAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(OrderModel orders) {
                                OrderDetailsFragment orderDetailsFragment = new OrderDetailsFragment();
                                Bundle args = new Bundle();
                                args.putInt("ORDER_ID", orders.getIdorder());
                                orderDetailsFragment.setArguments(args);
                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.content_frame, orderDetailsFragment);
                                fragmentTransaction.addToBackStack(orderDetailsFragment.getClass().getName());
                                fragmentTransaction.commit();
                            }
                        }));
                    }

                    @Override
                    public void onOrderFailure(String message) {
                        progressDialog.dismiss();
                        List<OrderModel> list = new ArrayList<>();
                        order_recycler_view.setLayoutManager(mLayoutManager1);
                        order_recycler_view.setItemAnimator(new DefaultItemAnimator());
                        order_recycler_view.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                        order_recycler_view.setAdapter(new MainOrderAdapter(getContext(), list));
                    }
                });
            }else{
                progressDialog.dismiss();
                List<OrderModel> list = new ArrayList<>();
                order_recycler_view.setLayoutManager(mLayoutManager1);
                order_recycler_view.setItemAnimator(new DefaultItemAnimator());
                order_recycler_view.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                order_recycler_view.setAdapter(new MainOrderAdapter(getContext(), list));
                Snackbar.make(view, "Cannot find orders for the retailer", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }else{
            progressDialog.dismiss();
            Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @OnClick(R.id.order_home_button)
    public void onHomeButtonClick(){
        AllRetailersFragment allRetailersFragment = new AllRetailersFragment();
        Bundle args = new Bundle();
        allRetailersFragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, allRetailersFragment);
        fragmentTransaction.commit();
    }
}