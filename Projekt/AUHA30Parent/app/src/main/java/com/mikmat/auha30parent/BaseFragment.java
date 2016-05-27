package com.mikmat.auha30parent;

import android.support.v4.app.Fragment;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by Mikkel on 27-05-2016.
 */
public class BaseFragment extends Fragment {

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim){
        if(FragmentUtils.sDisableFragmentAnimations){
            Animation animation = new AlphaAnimation(0,0);
            animation.setDuration(0);
            return  animation;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }
}
