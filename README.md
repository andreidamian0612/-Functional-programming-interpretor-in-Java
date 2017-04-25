322CB Damian Andrei Nicolae

Clasa Main : fac citirea din fisier si adaug tokenurile in array-ul pars,creez arborele si il evaluez.
Clasa Node : retin lista de fii,parintele,si informatia,am metode definite destul de intuitive.
Clasa Tree : Aici are loc toata magia.Tin intr-un hashmap variabilele si valorile corespunzatoare fiecarei variabila.
Metoda evalBool : verific o expresie booleana,cu toate cazurile posibile(daca elementele se afla sau nu in hashmap,daca sunt numerice)
Metoda evalIf = verifica un nod de tipul if.
Metoda evalAssert = verifica nod de tip assert.
Metoda evalReturn = verifica un nod de tip Return.
Metoda operatii = face operatiile,aici verific din nou fiecare caz,daca variabila se afla sau nu in hashmap,daca argumentul e numeric,etc.
In cazul in care se intalneste o variabila care nu e in hashmap(de ex return x + a,considerand ca x este in hashmap cu o valoare iar a nu este declarat) se adauga in erori eroarea "check failed".
Daca la finalul programului nu exista return, se adauga eroarea "missing return".
Daca assertul esueaza,se adauga eroarea "assert failed".
Adaug toate erorile intr-un vector pentru a putea tine cont de prioritate,in cazul in care se gasesc toate cele 3 erori,o afisez pe cea cu prioritate mai mare.

Fiecare nod este definit astfel :
IF = are 3 fii(conditia si cele 2 ramuri)
Return = un fiu
For = 4fii(i=1;i<n;i++ si instructiunea de executat in campul sau)
Egal = 2 fii(variabila si valoarea)
Assert = 1fiu(conditia booleana de verificat)
Metoda evalTree = face parcurgerea arborelui si verifica fiecare tip de nod.
Metoda eval = foloseste evalTree pentru a se construi vectorul de erori si afiseaza eroarea corespunzatoare daca e nevoie.