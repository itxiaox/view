package com.github.itxiaox.address.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.github.itxiaox.address.R;

import java.util.List;

public class AddressAdapter extends BaseAdapter implements SectionIndexer{

	private List<Content> list = null;
	private Context mContext;
	private SectionIndexer mIndexer;
	
	public AddressAdapter(Context mContext, List<Content> list) {
		this.mContext = mContext;
		this.list = list;

	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.xx_address_item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.name);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			
			
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		final Content mContactInfo = list.get(position);
		if (position == 0) {
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContactInfo.getLetter());
		} else {
			String lastCatalog = list.get(position - 1).getLetter();
			if (mContactInfo.getLetter().equals(lastCatalog)) {
				viewHolder.tvLetter.setVisibility(View.GONE);
			} else {
				viewHolder.tvLetter.setVisibility(View.VISIBLE);
				viewHolder.tvLetter.setText(mContactInfo.getLetter());
			}
		}
		viewHolder.tvTitle.setText(mContactInfo.getName()+"");
		
		return view;

	}
	


	final static class ViewHolder {
		TextView tvTitle;
		TextView tvLetter;
	}


	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getSectionForPosition(int position) {
		
		return 0;
	}

	public int getPositionForSection(int section) {
		Content mContactInfo;
		String l;
		if (section == '!') {
			return 0;
		} else {
			for (int i = 0; i < getCount(); i++) {
				mContactInfo = (Content) list.get(i);
				l = mContactInfo.getLetter();
				char firstChar = l.toUpperCase().charAt(0);
				if (firstChar == section) {
					return i + 1;
				}

			}
		}
		mContactInfo = null;
		l = null;
		return -1;
	}
}