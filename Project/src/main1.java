import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class main1 {
	public static void printTa(QueryImages query1,Scanner scan) {
		
	}

//bye
	public static void printTags(QueryImages query1,Scanner scan) {

		String input;


		do {
			do {
				System.out.print(">");
				input=scan.nextLine();

				if(!input.equals("exit")) {
					query1.AddTags(input);
					query1.DisplayTags();
				}

			}while(!input.equals("exit"));

		}while(query1.emptytags());


	}

	public static boolean CheckFolder(String folder) { //check the input folder

		File file = new File(folder);
		if (file.isDirectory()) { //checking if file exist

			return true;

		}
		else {
			System.out.println("Directory ["+folder+"] doesn't exist!!");
			return false;
		}

	}

	public static void main(String[] args) {


		Scanner scan=new Scanner(System.in);
		String input;

		QueryImages query1=new QueryImages();

		//File file=new File("images4/bmw1.jpg");
		//MetaReader.reader(file);


		System.out.println("Type folder name/folder path: <folder>");

		do {

			System.out.print(">");
			input=scan.nextLine();


		}while(!CheckFolder(input));

		MetaReader meta1=new MetaReader(input);
		meta1.createxml();

		System.out.println(" ");
		System.out.println("Type at least 1 keyword: <tag> ");
		System.out.println("Type exit when you are done");

		printTags(query1,scan);



		System.out.println(" ");

		ArrayList<String> option= new ArrayList<String>();
		option.add("1");
		option.add("2");
		option.add("3");
		option.add("exit");

		do {


			System.out.println("Press 1 to add another tag");
			System.out.println("Press 2 to delete tags");
			System.out.println("Press 3 for result");
			System.out.println("Type <exit> to leave");

			do {
				System.out.print(">");
				input=scan.nextLine();

			}while(!option.contains(input));

			switch(input) {


			case "1":
				query1.DisplayTags();
				System.out.println("Type tag to add: <tag> ");
				System.out.println("Type exit when you are done");
				printTags(query1,scan);
				System.out.println(" ");
				break;

			case "2":
				System.out.println(" ");
				query1.DisplayTags();
				System.out.println("Type tag to delete: <tag> ");
				System.out.println("Type exit when you are done");

				String input2 ;

				do {

					System.out.print(">");
					input2=scan.nextLine();


					if(query1.deleteTags(input2)) {
						System.out.println("tag removed ");

					}else if(!input2.equals("exit")) 
					{
						System.out.println("tag not found");
					}

				}while(!input2.equals("exit"));

				if(query1.emptytags()) {
					System.out.println("You need at least 1 Tag");
					printTags(query1,scan);
				}

				break;

			case "3":
				System.out.println(" ");
				query1.Extractxml("Default-xml.xml");
				query1.DisplayImages();

				break;

			case "exit":
				break;
			}

			if(!input.contains("exit")) {
				System.out.println("Press any key to return to the main menu or <exit> to leave ");

				input=scan.nextLine();
			}

		}while(!input.contains("exit"));


		scan.close();



	}

}