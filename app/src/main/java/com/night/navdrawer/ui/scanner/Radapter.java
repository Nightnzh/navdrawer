package com.night.navdrawer.ui.scanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.night.navdrawer.R;
import com.night.navdrawer.model.QrCode;

import java.util.List;

public class Radapter extends RecyclerView.Adapter<Radapter.QrCodeViewHolder> {

    private List<QrCode> qrCodes;
    private final LayoutInflater layoutInflater;

    Radapter(Context context){layoutInflater = LayoutInflater.from(context);}

    @NonNull
    @Override
    public QrCodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_item,parent,false);
        return new  QrCodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QrCodeViewHolder holder, int position) {
        holder.bindTO(qrCodes.get(position));
    }

    @Override
    public int getItemCount() {
        if(qrCodes != null){
            return qrCodes.size();
        }else {
            return 0;
        }
    }

    class QrCodeViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewQrCode;

        public QrCodeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewQrCode = itemView.findViewById(R.id.textView_qr);
        }

        public void bindTO(QrCode qrCode){
            textViewQrCode.setText(qrCode.getQr());
        }
    }

    public List<QrCode> getQrCodes() {
        return qrCodes;
    }


    public void setQrCodes(List<QrCode> list){
        this.qrCodes = list;
        notifyDataSetChanged();
    }

}
