package com.example.demo.Servies;

import com.example.demo.Models.ATCD;
import com.example.demo.Models.Visite;

import java.util.List;

public interface ATCDServices {

    public ATCD create (ATCD atcd );
    public ATCD update (ATCD atcd);
    public List<ATCD> FindAll ();
    public ATCD FindOne (Long Id);
    public void delete (Long Id);
}
