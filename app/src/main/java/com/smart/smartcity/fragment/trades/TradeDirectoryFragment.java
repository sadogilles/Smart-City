package com.smart.smartcity.fragment.trades;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.smart.smartcity.R;
import com.smart.smartcity.adapters.TradeTypeListAdapter;
import com.smart.smartcity.context.global.IMainFragmentContext;
import com.smart.smartcity.context.trade.ITradeTypeListContext;
import com.smart.smartcity.dao.TradeTypeDAO;
import com.smart.smartcity.model.TradeType;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TradeDirectoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TradeDirectoryFragment extends Fragment implements ITradeTypeListContext, AdapterView.OnItemClickListener {

    private static final String CATEGORY_ID = "CATEGORY_ID";

    private TextView tradeDirectoryStatus;
    private ListView tradeTypesListView;
    private TradeTypeListAdapter tradesListAdapter;
    private List<TradeType> tradeTypes = new ArrayList<>();
    private TradeTypeDAO tradeTypeDAO;
    private Deque<Integer> tradeTypeStack = new ArrayDeque<>();

    public TradeDirectoryFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TradeDirectoryFragment newInstance(int categoryId) {
        TradeDirectoryFragment fragment = new TradeDirectoryFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(CATEGORY_ID, categoryId);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trade_directory, container, false);
        tradeDirectoryStatus = view.findViewById(R.id.trade_directory_status);

        Bundle args = getArguments();
        int categoryId = args.getInt(CATEGORY_ID);

        tradeTypesListView = view.findViewById(R.id.trades_directory_list_view);
        tradesListAdapter = new TradeTypeListAdapter(getActivity().getApplicationContext(), tradeTypes);
        tradeTypesListView.setAdapter(tradesListAdapter);
        tradeTypesListView.setOnItemClickListener(this);

        tradeTypeDAO = new TradeTypeDAO();
        tradeTypeDAO.setTradeTypeListContext(this);
        getContentAndPush(-1);

        return view;
    }

    private void getContentAndPush(int categoryId) {
        tradeTypeDAO.getContent(categoryId);
        tradeTypeStack.push(categoryId);
    }

    public void popAndGetContent() {
        tradeTypeStack.pop();

        tradeTypeDAO.getContent(tradeTypeStack.peek());
    }

    @Override
    public void onGetContentSuccessful(List<TradeType> tradeTypes) {
        this.tradeTypes.clear();
        this.tradeTypes.addAll(tradeTypes);
        tradesListAdapter.clear();
        if (tradeTypeStack.size() > 1) {
            tradesListAdapter.add(TradeType.createCancelObject());
        }
        tradesListAdapter.addAll(tradeTypes);
        tradesListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetContentFailure() {
        tradeDirectoryStatus.setText("Error : failed to load trade categories");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TradeType tradeType = tradeTypes.get(position);

        if (tradeType.isCancel()) {
            popAndGetContent();
        } else if (tradeType.getTradeId() != -1) {
            ((IMainFragmentContext) getActivity()).showTradeDetailsFragment(tradeType.getTradeId());
        } else {
            getContentAndPush(tradeType.getId());
        }
    }


}
