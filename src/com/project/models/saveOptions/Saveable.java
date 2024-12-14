package com.project.models.saveOptions;

import com.project.models.Corporation;
import com.project.models.Worker;

import java.util.List;

public interface Saveable
{
    public void save(String filename, Corporation corp);
    public List<Worker> load(String filename);
}
