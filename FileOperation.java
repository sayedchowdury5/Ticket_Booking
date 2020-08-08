
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Scanner;

public class FileOperation {

	/**
	 * Function to check if the data inside the file is exists
	 * @param checkthis - String to be checked
	 * @param filename - The name of the database file (in .txt)
	 * @param index - Which part of the line needs to be checked
	 * @return true if the string is exists
	 */
	public boolean checkExists(String checkthis, String filename, int index) {
		boolean found = false;
		try {
			File file = new File(filename);
			Scanner scan = new Scanner(file);

			while (scan.hasNext()) {
				String[] tokens = scan.nextLine().split(";");
				if (tokens[index].equals(checkthis)) {
					found = true;
					scan.close();
					break;
				}
			}
			scan.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return found;
	}
	
	/**
	 * Function to check if the string exists in the file. Only check the first part of every line.
	 * @param checkthis - String to be checked
	 * @param filename - The name of the database file (in .txt)
	 * @return true if the 
	 */
	public boolean checkExists(String checkthis, String filename) {
		boolean found = false;
		try {
			File file = new File(filename);
			Scanner scan = new Scanner(file);

			while (scan.hasNext()) {
				String[] tokens = scan.nextLine().split(";");
				if (tokens[0].equals(checkthis)) {
					found = true;
					scan.close();
					break;
				}
			}
			scan.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return found;
	}


	/**
	 * Function to write a new data of newly registered user inside the users database
	 * @param userEmail - User's email
	 * @param userPass - User's password
	 * @param userFullName - User's full name
	 * @return true if writing into the database is success
	 */
	public boolean writeInto(String string, String file) {
		try {
			final Path path = Paths.get(file);
			Files.write(Paths.get(file), Arrays.asList(string),
					StandardCharsets.UTF_8,
					Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Function to check if the login credentials are correct or not
	 * @param email - user email address
	 * @param password - user password
	 * @return true if the login details are correct
	 */
	public boolean checkCredential(String email, String password) {
		boolean success = false;
		try {
			File file = new File("users.txt");
			Scanner scan = new Scanner(file);

			while (scan.hasNext()) {
				String[] tokens = scan.nextLine().split(";");
				if (tokens[0].equals(email) && tokens[1].equals(password)) {
					success = true;
					scan.close();
					break;
				}
			}
			scan.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * Function to remove the specific line inside the specified file
	 * @param file - file name
	 * @param toRemove - line number to be removed
	 * @return true if the process of removing the line is success
	 * @throws IOException
	 */
	public boolean removeNthLine(String file, int toRemove) throws IOException {
	    File tmp = File.createTempFile("tmp", "");

	    BufferedReader br = new BufferedReader(new FileReader(file));
	    BufferedWriter bw = new BufferedWriter(new FileWriter(tmp));

	    for (int i = 0; i < toRemove; i++)
	        bw.write(String.format("%s%n", br.readLine()));

	    br.readLine();

	    String l;
	    while (null != (l = br.readLine()))
	        bw.write(String.format("%s%n", l));

	    br.close();
	    bw.close();

	    File oldFile = new File(file);
	    if (oldFile.delete())  {
	        tmp.renameTo(oldFile);
	        return true;
	    }
	    else
	    	return false;
	}

	/**
	 * Function to find the line number in a specified file
	 * @param data - the data that is being searched for
	 * @param filedb - file name
	 * @return line number
	 */
	public int findLine(String data, String filedb) {
		int lineNo = 0;
		try {
			File file = new File(filedb);
			Scanner scan = new Scanner(file);

			while (scan.hasNext()) {
				String[] tokens = scan.nextLine().split(";");
				if (tokens[0].equals(data)) {
					scan.close();
					break;
				}
				lineNo++;
			}
			scan.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lineNo;
	}
}
