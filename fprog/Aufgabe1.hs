type Nat0 = Integer
type Nat1 = Integer
type GesamtKugelZahl = Nat1
type GezogeneKugelZahl = Nat1
type Spiel = (GesamtKugelZahl,GezogeneKugelZahl)
type Gluecksspiel = (Spiel,Spiel)

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

--Hauptfunktion: Gibt die Position einer Zahl in der Fibonacci-Funktion an oder die Zahl selbst wenn sie nicht vorkommt
fib' :: Nat0 -> Nat0
fib' k
	| k==0 = 0
	| k==1 = 1
	| k>1 = fibnum(k,2)


--Hauptfunktion: Gibt die aufsteigende Fibonacci-Funktion bis zu n in einer liste aus
fibs:: Nat0 -> [Nat0]
fibs n = map (fibhelp) [0..n]


--Hilfsfunktion: Ueberprueft ob eine Zahl in der Fibonacci-Funktion vorkommt
--Wenn Ja, gibt die Position der Zahl in der Fibonacci-Funktion zurueck
--Wenn Nein, gib die Zahl selbst zurueck--
fibnum :: (Nat0,Nat0) -> Nat0
fibnum (k,n)
	| fibhelp n==k = n
	| fibhelp n>k = k
	| otherwise = fibnum(k,n+1)

--Hilfsfunktion: Berechnet die Fibonacci-Funktion bis zur n-ten Position
fibhelp :: Nat0 -> Nat0
fibhelp k
	| k==0 = 0
	| k==1 = 1
	| k>1 = fibhelp(k-1)+fibhelp(k-2)


--Hauptfunktion: Verflechtet zwei Listen miteinander
verflechten :: [Int] -> [Int] -> [Int]
verflechten (x:xs) (y:ys) = x : y : verflechten xs ys
verflechten x [] = x
verflechten [] y = y