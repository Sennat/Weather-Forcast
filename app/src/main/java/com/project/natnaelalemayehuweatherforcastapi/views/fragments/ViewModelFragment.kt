package com.project.natnaelalemayehuweatherforcastapi.views.fragments

import androidx.fragment.app.Fragment
import com.project.natnaelalemayehuweatherforcastapi.dependencyInjection.DependencyInjection

open class ViewModelFragment : Fragment() {

    protected val viewModelFragment by lazy {
        DependencyInjection.provideViewModel(requireActivity())
    }
}