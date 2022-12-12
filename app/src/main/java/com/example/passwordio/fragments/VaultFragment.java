package com.example.passwordio.fragments;

import android.content.Intent;
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

import com.example.passwordio.DB;
import com.example.passwordio.NoteItemsActivity;
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
public class VaultFragment extends Fragment implements  View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

//    private static final Login[] loginData = {
//            new Login(-1, "jayakrishnan_jayu", "1234", "open.spotify.com"),
//            new Login(-1, "jayakrishnan_jayu", "1234", "open.spotify.com"),
//            new Login(-1, "jayakrishnan_jayu", "1234", "open.spotify.com"),
//            new Login(-1, "jayakrishnan_jayu", "1234", "open.spotify.com"),
//            new Login(-1, "jayakrishnan_jayu", "1234", "open.spotify.com"),
//            new Login(-1, "jayakrishnan_jayu", "1234", "open.spotify.com"),
//            new Login(-1, "jayakrishnan_jayu", "1234", "open.spotify.com"),
//            new Login(-1, "jayakrishnan_jayu", "1234", "open.spotify.com"),
//            new Login(-1, "jayakrishnan_jayu", "1234", "open.spotify.com"),
//            new Login(-1, "jayakrishnan_jayu", "1234", "open.spotify.com"),
//            new Login(-1, "jayakrishnan_jayu", "1234", "open.spotify.com"),
//            new Login(-1, "jayakrishnan_jayu", "1234", "open.spotify.com"),
//    };

//    public static final Folder[] folderData = {
//            new Folder("Apple"),
//            new Folder("Crypto"),
//            new Folder("Desktop"),
//            new Folder("Github/Gitlab"),
//            new Folder("Google"),
//            new Folder("Network"),
//            new Folder("College"),
//    };

    private static final Folder[] typeData = {
            new Folder(-1, "Login", 7),
            new Folder(-1, "Secure Note", 5),
    };
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DB db;
    Folder[] folders;
    Login[] loginsNoFolder;

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
        db = new DB(getContext());
//        db.generateTestData();
        folders = db.allFolders();
//        loginsNoFolder = db.allLogins();
        loginsNoFolder = db.loginsByFolder(-1);
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
        LoginAdapter loginAdapter = new LoginAdapter(loginsNoFolder);

        FolderAdapter folderAdapter = new FolderAdapter(folders);

        view.findViewById(R.id.fragmentVaultNoteLayout).setOnClickListener(this);


        RecyclerView rv = view.findViewById(R.id.vaultNoFolderLoginRV);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(loginAdapter);

        RecyclerView folderRV = view.findViewById(R.id.vaultFolderRV);
        folderRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        folderRV.setAdapter(folderAdapter);

        TextView typeCountTV = view.findViewById(R.id.vaultTypeCount);
        TextView folderCountTV = view.findViewById(R.id.vaultFolderCount);
        TextView noFolderCountTV = view.findViewById(R.id.vaultNoFolderCount);
//        typeCountTV.setText(""+typeData.length);
        folderCountTV.setText(""+folders.length);
        noFolderCountTV.setText(""+loginsNoFolder.length);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragmentVaultNoteLayout:
                view.getContext().startActivity(new Intent(view.getContext(), NoteItemsActivity.class));
        }
    }
}
