package com.gadget_mart.admin.ui;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gadget_mart.admin.R;
import com.gadget_mart.admin.callbacks.RetailerSaveCallBack;
import com.gadget_mart.admin.model.RetailerModel;
import com.gadget_mart.admin.network.InternetConnectivity;
import com.gadget_mart.admin.service.RetailerService;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RetailerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RetailerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Unbinder unbinder;
    @BindView(R.id.retailer_name_edit_text)
    EditText retailer_name_edit_text;
    @BindView(R.id.retailer_email_edit_text)
    EditText retailer_email_edit_text;
    @BindView(R.id.retailer_phone_edit_text)
    EditText retailer_phone_edit_text;
    @BindView(R.id.retailer_address_edit_text)
    EditText retailer_address_edit_text;
    @BindView(R.id.retailer_submit_button)
    Button retailer_submit_button;
    private ProgressDialog progressDialog;

    public RetailerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RetailerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RetailerFragment newInstance(String param1, String param2) {
        RetailerFragment fragment = new RetailerFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retailer, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retailer_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetConnectivity.isConnectedToInternet(getContext())) {
                    if(validateForm(view) == true){
                        RetailerModel retailerModel = new RetailerModel();
                        retailerModel.setRetailerName(retailer_name_edit_text.getText().toString());
                        retailerModel.setRetailerImage("sample_retailer.png");
                        retailerModel.setEmail(retailer_email_edit_text.getText().toString());
                        retailerModel.setPhone(retailer_phone_edit_text.getText().toString());
                        retailerModel.setAvailable(true);
                        retailerModel.setAddress(retailer_address_edit_text.getText().toString());
                        retailerModel.setDateCraeted(null);
                        retailerModel.setLastUpdated(null);

                        progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage("Saving...");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();

                        RetailerService retailerService = new RetailerService();
                        retailerService.saveRetailer(getContext(), retailerModel, new RetailerSaveCallBack() {
                            @Override
                            public void onRetailerSaveSuccess(String message, int code) {
                                progressDialog.dismiss();
                                AllRetailersFragment allRetailersFragment = new AllRetailersFragment();
                                Bundle args = new Bundle();
                                allRetailersFragment.setArguments(args);
                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.content_frame, allRetailersFragment);
                                fragmentTransaction.commit();
                            }

                            @Override
                            public void onRetailerSaveFailure(String message, int code) {
                                progressDialog.dismiss();
                                Snackbar.make(view, "Please try again.....", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        });
                    }

                }else{
                    Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    public boolean validateForm(View view) {
        boolean validate = true;

        if (retailer_name_edit_text.getText().toString() == null && retailer_name_edit_text.getText().toString().isEmpty()) {
            validate = false;
            Snackbar.make(view, "Please enter username", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        if (retailer_email_edit_text.getText().toString() == null && retailer_email_edit_text.getText().toString().isEmpty()) {
            validate = false;
            Snackbar.make(view, "Please enter valid email", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        if (retailer_phone_edit_text.getText().toString() != null && !retailer_phone_edit_text.getText().toString().isEmpty()) {
            if (retailer_phone_edit_text.getText().toString().length() < 10) {
                validate = false;
                Snackbar.make(view, "Please enter valid mobile number", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }

        if (retailer_address_edit_text.getText().toString() == null && retailer_address_edit_text.getText().toString().isEmpty()) {
            validate = false;
            Snackbar.make(view, "Please enter address", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        return validate;
    }

}