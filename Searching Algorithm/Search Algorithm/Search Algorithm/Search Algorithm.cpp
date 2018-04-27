// Search Algorithm.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <iostream>
#include <string>

using namespace std;

int LinearSearch(int array[], int size, int searchValue)
{
	for (int i = 0; i < size; i++) {
		if (searchValue == array[i]) {
			return i;
		}
	}

	return -1;
}

int BinarySearch(char array[], int size, char SearchValue) {

	int low = 0;
	int high = size - 1;

	int mid;
	while (low <= high) {
		mid = (low + high) / 2;

		if (SearchValue == array[mid]) {
			return mid;
		}
		else if (SearchValue > array[mid]) {
			low = mid + 1;
		}
		else {
			high = mid - 1;
		}
	}

	return -1;
}
	int BinarySearch(string Filmarray[], int size, string value)
	{
		int first = 0,             
			last = size - 1,       
			middle,                
			position = -1;         
		bool found = false;        

		while (!found && first <= last)
		{
			middle = (first + last) / 2;
			if (Filmarray[middle] == value)      
			{
				found = true;
				position = middle;
			}
			else if (Filmarray[middle] > value)  
				last = middle - 1;
			else
				first = middle + 1;          
		}
		return position;
	}







int main()
{
	/*
	int a[] = { 15,23,7,45,87,16 }; // Array of numbers

	int userValue; // User variable

	cout << "Enter an Interger: " << endl; // Display text to infomr user
	cin >> userValue; // Asks user for input data

	int result = LinearSearch(a, 6, userValue);   //Name, array name, array size, variable

	if (result >= 0) {
		cout << "The number " << a[result] << " was found as the element with index " << result << endl;
	}
	else {
		cout << "The number " << userValue << " was not found. " << endl;
	}
	*/

	/*char BinaryArray[] = { 'A', 'B' , 'D', 'E', 'F', 'G', 'H'  }; // Simple array. Array is sosrted lowest to highest.

	char UserInput;

	cout << "Search for a film; " << endl;
	cin >> UserInput;

	char result = BinarySearch(BinaryArray, 8, UserInput);

	if (result >= 0) {
		cout << "The film you searched for " << BinaryArray[result] << " was found at element index " << result << endl;
	}
	else {
		cout << "The film you searched for " << UserInput <<  " wasn't found" << endl;
	}
	*/
	

		const int Films = 5;
		string Filmarray[Films] = { "Anaconda", "Birthday", "Cartoon", "Doodling", "Elephant", };

		string UserInput;
		int results;

		cout << "Enter the name of the film you would like to search for: ";
		cin >> UserInput;

		results = BinarySearch(Filmarray, Films, UserInput);

		
		if (results == -1)
			cout << "That film does not exsist at this current time"<<endl;
		else
		{
			cout << "The name is at element " << results;
			cout << " in the array.\n";
		}

		system("PAUSE");

		return 0;
	}
