package com.techticz.app.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techticz.app.R;
import com.techticz.app.base.AppCore;
import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.UseCases;
import com.techticz.app.domain.interactor.IExtractNutritientUseCase;
import com.techticz.app.domain.model.pojo.DayMeals;
import com.techticz.app.domain.model.pojo.NutitionInfo;
import com.techticz.app.ui.activity.DailyRoutineActivity;
import com.techticz.app.ui.customview.CircleItemView;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     DayMealInfoFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 * <p>You activity (or fragment) needs to implement {@link DayMealInfoFragment.Listener}.</p>
 */
public class DayMealInfoFragment extends BottomSheetDialogFragment implements IExtractNutritientUseCase.Callback {

    // TODO: Customize parameter argument names
    private static final String ARG_ITEM_COUNT = "item_count";
    private Listener mListener;

    // TODO: Customize parameters
    public static DayMealInfoFragment newInstance() {
        final DayMealInfoFragment fragment = new DayMealInfoFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.plan_details_bottom_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        IExtractNutritientUseCase usecase = (IExtractNutritientUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(getContext(), UseCases.CALCULATE_NUTRI);
        int current = ((DailyRoutineActivity)getActivity()).getCurrentActiveSection();
        DayMeals dayMeals = ((DailyRoutineActivity) getActivity()).getDayMealIdsByDay(current);

        usecase.execute(this,true,null,dayMeals,null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        /*if (parent != null) {
            mListener = (Listener) parent;
        } else {
            mListener = (Listener) context;
        }*/
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    @Override
    public void onError(AppErrors error) {

    }

    @Override
    public void onNutritionFetched(NutitionInfo meal, NutitionInfo dayMeals, NutitionInfo plan) {
        loadUI(dayMeals);
    }

    private void loadUI(NutitionInfo info) {
        View view = getView();
        ((CircleItemView)view.findViewById(R.id.civ_calory)).set("Calory",info.getCalory());
        ((CircleItemView)view.findViewById(R.id.civ_carbs)).set("Carbs",info.getCarbs());
        ((CircleItemView)view.findViewById(R.id.civ_fat)).set("Fat",info.getFat());
        ((CircleItemView)view.findViewById(R.id.civ_protine)).set("Protine",info.getProtine());
        ((CircleItemView)view.findViewById(R.id.civ_fiber)).set("Fiber",info.getFiber());

        ((CircleItemView)view.findViewById(R.id.civ_va)).set("Vitamin A",info.getVitaminA());
        ((CircleItemView)view.findViewById(R.id.civ_vb)).set("Vitamin B12",info.getVitaminB());
        ((CircleItemView)view.findViewById(R.id.civ_vc)).set("Vitamin C",info.getVitaminC());
        ((CircleItemView)view.findViewById(R.id.civ_vd)).set("Vitamin D",info.getVitaminD());
        ((CircleItemView)view.findViewById(R.id.civ_ve)).set("Vitamin E",info.getVitaminE());
        ((CircleItemView)view.findViewById(R.id.civ_vk)).set("Vitamin K",info.getVitaminK());

        ((CircleItemView)view.findViewById(R.id.civ_sodium)).set("Sodium",info.getSodium());
        ((CircleItemView)view.findViewById(R.id.civ_potasium)).set("Potassium",info.getPotassium());
        ((CircleItemView)view.findViewById(R.id.civ_magnisium)).set("Maganessium",info.getMagnissium());
        ((CircleItemView)view.findViewById(R.id.civ_iron)).set("Iron",info.getIron());
        ((CircleItemView)view.findViewById(R.id.civ_zinc)).set("Zinc",info.getZinc());
        ((CircleItemView)view.findViewById(R.id.civ_calcium)).set("Calcium",info.getCalcium());

        ((CircleItemView)view.findViewById(R.id.civ_sugar)).set("Sugar",info.getSugar());
        ((CircleItemView)view.findViewById(R.id.civ_cholestral)).set("Cholestrol",info.getCholestrol());
    }

    public interface Listener {
        void onItemClicked(int position);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        final TextView text;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            // TODO: Customize the item layout
            super(inflater.inflate(R.layout.fragment_item_list_dialog_item, parent, false));
            text = (TextView) itemView.findViewById(R.id.text);
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClicked(getAdapterPosition());
                        dismiss();
                    }
                }
            });
        }

    }

    private class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final int mItemCount;

        ItemAdapter(int itemCount) {
            mItemCount = itemCount;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.text.setText(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return mItemCount;
        }

    }

}
