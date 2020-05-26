package com.smart.smartcity.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smart.smartcity.R;
import com.smart.smartcity.model.Publication;

import java.util.List;

public class PublicationListAdapter extends ArrayAdapter<Publication> {
    private LayoutInflater inflater;

    public PublicationListAdapter(Context context, List<Publication> data) {
        super(context, 0, data);

        inflater = LayoutInflater.from(context);
    }

    @Override
    public long getItemId(int position) {
        Publication publication = (Publication) getItem(position);

        return publication.getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        PublicationListAdapter.ViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.publication_list_adapter, null);

            viewHolder = new PublicationListAdapter.ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (PublicationListAdapter.ViewHolder) view.getTag();
        }

        Publication publication = (Publication) getItem(position);

        viewHolder.authorAndDate.setText(Html.fromHtml("By <b>" + publication.getAuthorName() + "</b> at <b>" + publication.getFormattedDate() + "</b>"));
        viewHolder.content.setText(publication.getContent());

        return view;
    }

    public class ViewHolder {
        private TextView authorAndDate;
        private TextView content;
        private ImageView avatar;

        public ViewHolder(View view) {
            authorAndDate = view.findViewById(R.id.publication_author_date);
            content = view.findViewById(R.id.publication_content);
            avatar = view.findViewById(R.id.publication_avatar);
        }
    }
}
