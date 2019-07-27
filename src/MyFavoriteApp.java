
import java.io.File; 
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.List;
//packages needed for program

public class MyFavoriteApp { //start of program

	//Symptom to illnesses map
	private Map<String, List<String>> symptomChecker;
	//Scanner called keyboard which will take input name for file
	Scanner keyboard = new Scanner(System.in); 

	//Constructor symptomChecker map using TreeMap
	public MyFavoriteApp() {

		//Use TreeMap, i.e. sorted order keys
		symptomChecker = new TreeMap<String, List<String>>();
	} 

	// end default constructor
	
	/* Reads a text file of illnesses and their symptoms.
	   Each scanFile in the file has the form
		Illness: Symptom, Symptom, Symptom, ...  
	   Store data into symptomChecker map */
	
	
	public void processDatafile() 
	{
		// Step 1: read in a data filename from keyboard
		System.out.println("Enter filename:");
		String filename = keyboard.nextLine();
		
		// create a scanner for the file
		Scanner symtomsFile;
		try {
			symtomsFile = new Scanner(new File(filename));
			
			// Step 2: process data scanFiles in file scanner
			while (symtomsFile.hasNextLine()) {
				String scanFile = symtomsFile.nextLine();
				
				// 2.1 for each scanFile, split the scanFile into a disease and symptoms
				//     make sure to trim() spaces and use toLowercase()
				String[] values = scanFile.trim().toLowerCase().split(":");
				String disease = values[0].trim();
				ArrayList<String> symptoms = new ArrayList<String>();
				
				// 2.2 for symptoms, split into individual symptom
				// useDelimeter(",") to split into individual symptoms
				// make sure to trim() spaces and use toLowercase() for each
				// symptom
				symptoms.addAll(Arrays.asList(values[1].trim().toLowerCase().split(",")));
				
				// if it is already in the map, insert disease into related list
				for (String symptom : symptoms) {
					if (symptomChecker.containsKey(symptom.trim())) {
						symptomChecker.get(symptom.trim()).add(disease);
					} else {
						
						// if it is not already in the map, create a new list
						// with the disease
						ArrayList<String> diseases = new ArrayList<String>();
						diseases.add(disease);
						symptomChecker.put(symptom.trim(), diseases);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Step 3: display symptomChecker map
		System.out.println("======================================================");
		System.out.println("Contents of the Map: ");
		for (Entry<String, List<String>> entry : symptomChecker.entrySet()) {
			String key = entry.getKey().toString();
			List<String> values = entry.getValue();
			System.out.println(key + "=" + values);
		}
		
		// Step 1: get all possible symptoms from symptomChecker map
		//         and display them		
		System.out.println("The possible symptoms are:");
		for (Entry<String, List<String>> entry : symptomChecker.entrySet()) {
			String key = entry.getKey().toString();
			System.out.println(key);
		}
		
		System.out.println();	
	} // end processDatafile
	
	
	ArrayList<String> validateInput(String[] symptomsEntered)
	{
		ArrayList<String> al = new ArrayList<String>();
		
		// Step 2B: process patient symptoms, add to patientSymptoms list 
		//         display invalid/duplicate symptoms
		//         add valid symptoms to patientSymptoms list
		
		for(int i = 0; i < symptomsEntered.length; ++i) 
		{
			if(symptomsEntered[i].length() == 0) { continue; }

			String current = symptomsEntered[i];

			int count = 0;
			for(int j = 0; j < symptomsEntered.length; ++j) {			
				if(symptomsEntered[j].compareTo(current) == 0) { count += 1; }
			}
			
			symptomsEntered[i] = "";

			// If an input is present for more than 1 time, the input is duplicate.
			if(count > 1)
			{
				System.out.println(" => duplicate symptom: " + current);
				continue;
			}
			
			// cough,rash,cough,fever,blisterx,cough,runny NOSE
						
			// If a symptom is not there, the input is invalid.
			boolean valid = false;
			for (Entry<String, List<String>> entry : symptomChecker.entrySet()) {
				String key = entry.getKey().toString();
				if(key.compareTo(current) == 0) { valid = true; }
			}		
			
			if(valid == false)
			{
				System.out.println(" => invalid symptom: " + current);
			}
			else
			{
				al.add(current);
			}		
		}
		
		return al;		
	}
	
	public void processSymptoms() 
	{
		System.out.println("======================================================\n");

		// Step 2A: process patient symptoms, add to patientSymptoms list 
		//         read patient's symptoms in a scanFile, separated by ','
		//         create a scanner for symptoms 
		//         UseDelimeter(",") to split into individual symptoms 
		//         make sure to trim() spaces and use toLowercase()


		System.out.println("Enter symptoms: ");
		Scanner input = new Scanner(System.in);
		input.hasNext();
		
		String symptoms = input.nextLine();
		String[] symptomsEntered = symptoms.trim().toLowerCase().split(",");
		
		// Trim each item individually.
		for(int i = 0; i < symptomsEntered.length; ++i) {
			symptomsEntered[i] = symptomsEntered[i].trim();	
		}
		
		// The final "PatientSymptoms" array which contains valid inputs.
		ArrayList<String> PatientSymptoms = validateInput(symptomsEntered);
		
		System.out.println("\n======================================================\n");

		// Step 3: display patientSymptoms list
		System.out.print("PatientSymptoms List: [");
		for(int i = 0; i < PatientSymptoms.size(); ++i) 
		{	
			System.out.print(PatientSymptoms.get(i));
			
			if(i < PatientSymptoms.size() - 1)
			{
				System.out.print(", ");				
			}
		}
		System.out.println("]\n");
		
		System.out.println("Possible illnesses that match any symptom are:\n");
		
	    // Step 4: process illnesses to frequency count
		//         create a map of disease name and frequency count
		//         for each symptom in patientSymptoms list
		//              get list of illnesses from symptomChecker map
		//              for each illness in the list
		// 	            if it is already in the map, increase counter by 1
	        //	            if it is not already in the map, create a new counter 1
		//         ** need to keep track of maximum counter numbers		
		Map<String, List<String>> symptom_list = new TreeMap<String, List<String>>();
		
		
		// Count the number of times an illness appears. 
		// An Array is used as a counter, the size of an array is the number of times an illness appears.
		for(int i = 0; i < PatientSymptoms.size(); ++i) 
		{			
			for (Entry<String, List<String>> entry : symptomChecker.entrySet()) 
			{
				String key = entry.getKey().toString();
				if(key.compareTo(PatientSymptoms.get(i)) == 0) 
				{ 
					List<String> values = entry.getValue();
					
					for(int k = 0; k < values.size(); ++k) 
					{
						if(symptom_list.containsKey(values.get(k)))
						{		
							symptom_list.get(values.get(k)).add(" ");
						}
						else
						{
							symptom_list.put(values.get(k), new ArrayList<String>());	
							symptom_list.get(values.get(k)).add(" ");
						}
					}
				}
			}
			
		}
		
		// Step 5: display result
		//         for count i = 1 to maximum counter number
		//             display illness that has count i		
		for(int i = 0; i < PatientSymptoms.size(); ++i) 
		{		
			int num_symptom = i + 1;
			System.out.println("==> Disease(s) match " + (num_symptom) + " symptom(s)");
			
			for (Entry<String, List<String>> entry : symptom_list.entrySet()) 
			{
				String key = entry.getKey().toString();
					
				if(entry.getValue().size() == num_symptom)
				{
					System.out.println(key);		
				}	
			}	
			System.out.println();
		}
					
	}		// end processSymptoms
		
	public static void main(String[] args) {
		MyFavoriteApp physiciansHelper = new MyFavoriteApp();
		physiciansHelper.processDatafile();
		physiciansHelper.processSymptoms();
	} // end main

} // end PhysiciansHelper

