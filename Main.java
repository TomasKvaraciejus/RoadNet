//3. Duotas kelių sąrašas: miestas, miestas, atstumas. Rasti trumpiausią kelionės iš vieno duoto
//miesto į kitą duotą miestą maršrutą ir jo ilgį. Numatyti atvejį, kad toks maršrutas neegzistuoja.
//(grafo realizacija paremta kaimynystės matrica)

import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static void main(String args[])
    {
        try
        {
            Vector<String> cityList = new Vector<String>();
            File roadInput = new File("roads.txt");
            Scanner roadScanner = new Scanner(roadInput);
            int roadNetworkSize = roadScanner.nextInt();
            int roadNetwork[][] = new int[roadNetworkSize][roadNetworkSize];
            for (int i = 0; i < roadNetworkSize; ++i)
            {
                for (int x = 0; x < roadNetworkSize; ++x)
                {
                    roadNetwork[i][x] = -1;
                }
            }
            while(roadScanner.hasNextLine()) {
                String cityA = roadScanner.next(), cityB = roadScanner.next();
                int dist = roadScanner.nextInt(), indexA = getCityIndex(cityList, cityA), indexB = getCityIndex(cityList, cityB);

                System.out.println(cityA + " " + cityB + " " + indexA + " " + indexB);

                roadNetwork[indexA][indexB] = dist;
                roadNetwork[indexB][indexA] = dist;
            }

            Scanner input = new Scanner(System.in);

            int visitedCities[] = new int[cityList.size()];

            int startIndex = getCityIndex(cityList, input.next()), destIndex = getCityIndex(cityList, input.next());
            visitedCities[startIndex] = 1;
            int minCost = findPath(0, startIndex, destIndex, 0, -1, roadNetwork, cityList, visitedCities);

            System.out.println(minCost);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found!");
        }
    }

    static int findPath(int index, int startIndex, int destIndex, int curCost, int minCost, int roadNetwork[][], Vector<String> cityList, int visitedCities[])
    {
        while(index < cityList.size())
        {
            if(roadNetwork[startIndex][index] != -1 && visitedCities[index] == 0)
            {
                visitedCities[index] = 1;
                curCost += roadNetwork[startIndex][index];
                if(index == destIndex)
                {
                    if(minCost == -1 || curCost < minCost)
                    {
                        visitedCities[index] = 0;
                        minCost = curCost;
                    }
                }
                minCost = findPath(0, index, destIndex, curCost, minCost, roadNetwork, cityList, visitedCities);
                curCost -= roadNetwork[startIndex][index];
                visitedCities[index] = 0;
            }
            ++index;
        }

        return minCost;
    }

    static int getCityIndex(Vector<String> cityList, String cityName)
    {
        int cityIndex = -1;

        if(cityList.size() == 0)
        {
            cityList.add(cityName);
        }
        for (int x = 0; x < cityList.size(); ++x)
        {
            if(cityName.equals(cityList.elementAt(x)))
            {
                cityIndex = x;
            }

            if(cityIndex != -1)
            {
                x = cityList.size();
            }

            if(x == cityList.size() - 1)
            {
                if(cityIndex == -1)
                {
                    cityList.add(cityName);
                    cityIndex = cityList.size();
                }
            }
        }

        return cityIndex;
    }
}
