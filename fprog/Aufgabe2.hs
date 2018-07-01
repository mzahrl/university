-- Aufgabe 1
type N1 = Int

--Hauptfunktion
p2p :: (N1, N1) -> (N1, N1)
p2p (m, n)
    | m == n    = (1, 1)
    | otherwise = ggf (m, 1) (n, 1)

-- Hilfsfunktion: Findet das groeßte gemeinsame Vielfache zweier Zahlen
ggf:: (N1, N1) -> (N1, N1) -> (N1, N1)
ggf (m, p) (n, q)
  | m*p == n*q  = (p,q)
  | m*p < n*q   = ggf (m, (p + 1)) (n, q)
  | otherwise   = ggf (m, p) (n, (q + 1))


-- Aufgabe 2
type Nat0               = Integer
type Nat1               = Integer
type GesamtKugelZahl    = Nat1
type GezogeneKugelZahl  = Nat1
type Spiel              = (GesamtKugelZahl, GezogeneKugelZahl)
type Gluecksspiel       = (Spiel, Spiel)
type AngeboteneSpiele   = [Gluecksspiel]

--Hauptfunktion
attraktiveSpieleVorne :: AngeboteneSpiele -> [Gluecksspiel]
attraktiveSpieleVorne []      = []
attraktiveSpieleVorne (n:ns)  = attraktiveSpieleVorne     [m | m <- ns,     anzahlWettKombis m >  anzahlWettKombis n]
                                ++ sort       [m | m <- (n:ns), anzahlWettKombis m == anzahlWettKombis n]
                                ++ attraktiveSpieleVorne  [m | m <- ns,     anzahlWettKombis m <  anzahlWettKombis n]

--Hilffunktion
sort :: [Gluecksspiel] -> [Gluecksspiel]
sort []     = []
sort (n:ns) = sort    [m | m <- ns, gezogeneKugelAnzahl m >  gezogeneKugelAnzahl n]
                          ++ [n | anzahlWettKombis n /= 0]
                          ++ sort [m | m <- ns, gezogeneKugelAnzahl m <= gezogeneKugelAnzahl n]

--Hilftsfunktion
anzahlWettKombis :: Gluecksspiel -> Nat0
anzahlWettKombis ((n,k),(n',k')) = binom (n,k) * binom (n',k')

--Hilfsfunktion: Berechnet den Binomialkoeffizienten
binom :: (Integer,Integer) -> Integer
binom (n,k) 
	| k>n = 0
	| otherwise = div (fac n) (fac k * fac (n-k))

--Hilfsfunktion: Fakultaetsfunktion (!)
fac :: Integer -> Integer
fac n = if n == 0 then 1 else n * fac (n - 1)

--Hilffunktion: Ermittelt die gezogenen Kugeln bei einem Gluecksspiel
gezogeneKugelAnzahl :: Gluecksspiel -> Integer
gezogeneKugelAnzahl g = snd(fst(g))+snd(snd(g))


--Aufgabe 3:
type Toepfchen = [Int]
type Kroepfchen = [Int]
type Zahlenliste = [Int]
aufteilen :: Zahlenliste -> (Toepfchen,Kroepfchen)
aufteilen [] = ([],[])
aufteilen (n:ns) = ([i | i <- ns, anzahlEinsen(toBase3 i) `mod` 3 == 0],[i | i <- ns, anzahlEinsen(toBase3 i) `mod` 3 /= 0]) 
			
	
--Hilfsfunktion: Wandel eine Dezimalzahl in eine Zahl der Basis 3 um
toBase3 :: Int -> [Int]
toBase3 0 = [0]
toBase3 1 = [1]
toBase3 2 = [2]
toBase3 n 
	| n `mod` 3 == 1 = toBase3 (n `div` 3) ++ [1]
        | n `mod` 3 == 2 = toBase3 (n `div` 3) ++ [2]
	| n `mod` 3 == 0 = toBase3 (n `div` 3) ++ [0]


--Hilfsfunktion: Berechnet die Anzahl der Einser in einer Liste
anzahlEinsen:: [Int]->Int
anzahlEinsen [] = 0
anzahlEinsen (n:ns)
	| n==1 = 1+(anzahlEinsen ns)
	| otherwise = anzahlEinsen ns










--Aufgabe 4
type Nat = [Int]
ziffern = [0,1,2,3,4,5,6,7,8,9] :: [Int]


istGueltig :: Nat -> Bool
istGueltig [] = False
istGueltig [0] = True
istGueltig n = all (<9) n && all (>=0) n


normalForm :: Nat -> Nat
normalForm [] = []
normalForm [0] = [0]
normalForm n
	| istGueltig n == False = []
	| head n == 0 = normalForm (tail n)
	| otherwise = n


addiere :: Nat -> Nat -> Nat
addiere n m
	| istGueltig n == False = []
	| istGueltig m == False = []
	| otherwise = reverse(addiereZiffer(fst(gleicheLaenge n m),snd(gleicheLaenge n m),0))

subtrahiere :: Nat -> Nat -> Nat
subtrahiere n m
	| istGueltig n == False = []
	| istGueltig m == False = []
	| otherwise = reverse(subtrahiereZiffer(fst(gleicheLaenge n m),snd(gleicheLaenge n m),0))



gleicheLaenge :: Nat -> Nat -> (Nat,Nat)
gleicheLaenge n m
	| length n > length m = gleicheLaenge n  (0 : m)
	| length m > length n = gleicheLaenge (0 : n) m
	| otherwise = (reverse(n),reverse(m))

addiereZiffer :: (Nat,Nat,Int) -> Nat
addiereZiffer ([],[],u) = []
addiereZiffer (n,m,u) = [(head n + head m + u) `mod` 10+u] ++ addiereZiffer(tail n,tail m,(head n + head m) `div` 10)

subtrahiereZiffer:: (Nat,Nat,Int) -> Nat
subtrahiereZiffer ([],[],u) = []
subtrahiereZiffer (n,m,u)
		| head n - head m + u >= 0 = [(head n - head m + u) `mod` 10] ++ subtrahiereZiffer(tail n,tail m,(head n + head m) `div` 10)
		| head n - head m + u < 0 && length n == 1 = [((head n - head m + u)) `mod` 10] ++ [(head n + head m) `div` 10]
		| otherwise = [((head n - head m + u)) `mod` 10] ++ subtrahiereZiffer(tail n,tail m,(head n + head m) `div` 10)


