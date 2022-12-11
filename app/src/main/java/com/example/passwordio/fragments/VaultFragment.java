package com.example.passwordio.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordio.R;
import com.example.passwordio.adapters.FolderAdapter;
import com.example.passwordio.adapters.LoginAdapter;
import com.example.passwordio.models.Folder;
import com.example.passwordio.models.Login;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VaultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VaultFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final Login[] loginData = {
            new Login("jayakrishnan_jayu", "1234", "open.spotify.com"),
            new Login("jayakrishnan_jayu", "1234", "open.spotify.com"),
            new Login("jayakrishnan_jayu", "1234", "open.spotify.com"),
            new Login("jayakrishnan_jayu", "1234", "open.spotify.com"),
            new Login("jayakrishnan_jayu", "1234", "open.spotify.com"),
            new Login("jayakrishnan_jayu", "1234", "open.spotify.com"),
            new Login("jayakrishnan_jayu", "1234", "open.spotify.com"),
            new Login("jayakrishnan_jayu", "1234", "open.spotify.com"),
            new Login("jayakrishnan_jayu", "1234", "open.spotify.com"),
            new Login("jayakrishnan_jayu", "1234", "open.spotify.com"),
            new Login("jayakrishnan_jayu", "1234", "open.spotify.com"),
            new Login("jayakrishnan_jayu", "1234", "open.spotify.com"),
    };

    public static final Folder[] folderData = {
            new Folder("Apple", 7, R.drawable.ic_baseline_folder_open_30),
            new Folder("Crypto", 5,R.drawable.ic_baseline_folder_open_30),
            new Folder("Desktop", 2,R.drawable.ic_baseline_folder_open_30),
            new Folder("Github/Gitlab", 3,R.drawable.ic_baseline_folder_open_30),
            new Folder("Google", 3,R.drawable.ic_baseline_folder_open_30),
            new Folder("Network", 5,R.drawable.ic_baseline_folder_open_30),
            new Folder("College", 7,R.drawable.ic_baseline_folder_open_30),
    };

    private static final Folder[] typeData = {
            new Folder("Login", 7, R.drawable.ic_baseline_web_16),
            new Folder("Secure Note", 5,R.drawable.ic_baseline_note_24),
    };
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VaultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VaultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VaultFragment newInstance(String param1, String param2) {
        VaultFragment fragment = new VaultFragment();
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
        return inflater.inflate(R.layout.fragment_vault, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginAdapter loginAdapter = new LoginAdapter(loginData);
        FolderAdapter folderAdapter = new FolderAdapter(folderData);
        FolderAdapter typeAdapter = new FolderAdapter(typeData);

        RecyclerView typeRV = view.findViewById(R.id.vaultTypesRV);
        typeRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        typeRV.setAdapter(typeAdapter);


        RecyclerView rv = view.findViewById(R.id.vaultNoFolderLoginRV);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(loginAdapter);

        RecyclerView folderRV = view.findViewById(R.id.vaultFolderRV);
        folderRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        folderRV.setAdapter(folderAdapter);

        TextView typeCountTV = view.findViewById(R.id.vaultTypeCount);
        TextView folderCountTV = view.findViewById(R.id.vaultFolderCount);
        TextView noFolderCountTV = view.findViewById(R.id.vaultNoFolderCount);
        typeCountTV.setText(""+typeData.length);
        folderCountTV.setText(""+folderData.length);
        noFolderCountTV.setText(""+loginData.length);
    }
}
