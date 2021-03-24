package com.example.testemployees.screens.employees;

import com.example.testemployees.api.ApiFactory;
import com.example.testemployees.api.ApiService;
import com.example.testemployees.pojo.EmployeeResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EmployeeListPresenter {

    private CompositeDisposable compositeDisposable;
    private EmployeesListView view;

    public EmployeeListPresenter(EmployeesListView view) {
        this.view = view;
    }

    public void loadData(){

        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = apiService.getEmployees()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EmployeeResponse>() {
                    @Override
                    public void accept(EmployeeResponse employeeResponse) throws Exception {
                        view.showData(employeeResponse.getEmployees());


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showError();


                    }
                });
        compositeDisposable.add(disposable);



    }

    public void disposeDisposable(){
        if(compositeDisposable != null){
            compositeDisposable.dispose();
        }

    }


    }

