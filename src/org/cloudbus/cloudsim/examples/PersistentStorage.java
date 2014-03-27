/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cloudbus.cloudsim.examples;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tamojit9
 */
public class PersistentStorage implements Serializable{
    
    private static Map<String, Object> persistentMap = null;
    private static final long serialVersionUID = 1232325632312L;
    
    public static boolean load(File from) throws FileNotFoundException, IOException, ClassNotFoundException {
        InputStream is = new BufferedInputStream(new FileInputStream(from));
        ObjectInputStream ois = new ObjectInputStream(is);
        persistentMap = (Map<String, Object>) ois.readObject();
        return persistentMap != null;
    }
    
    public static boolean store(File to) throws FileNotFoundException, IOException {
        OutputStream os = new BufferedOutputStream(new FileOutputStream(to));
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(persistentMap);
        oos.close();
        return true;
    }
    
    public static void put(String key, Object value) throws Exception {
        if(persistentMap == null) {
            persistentMap = new HashMap<>();
        }
        if(value instanceof Serializable) {
            persistentMap.put(key, value);
        } else {
            throw new Exception("Provided Object cannot be serialized");
        }
    }
    
    public static Object get(String key) throws Exception {
        if(persistentMap == null) {
            persistentMap = new HashMap<String, Object>();
            throw new Exception("Please Load from storage first");
        }
        if(persistentMap.containsKey(key)) {
            return persistentMap.get(key);
        } else {
            throw new Exception("No Value found for the provided Key");
        }
    }
}