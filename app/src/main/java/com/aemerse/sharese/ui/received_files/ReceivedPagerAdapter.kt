package com.aemerse.sharese.ui.received_files

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.aemerse.sharese.utils.fileTypeList


class ReceivedPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return ReceivedFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence? = fileTypeList[position].name

    override fun getCount(): Int = fileTypeList.size
}