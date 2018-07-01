Aufgabe 1

>data Nat = Null | N Nat

Nat zu Instanz von Show


>instance Show Nat where
>	show (Null) = "0"
>	show (N n) = toBinary(fromEnum(N n)) 

Nat zu Instanz von Eq	
	
>instance Eq Nat where
>	Null == Null = True
>	N n1 == N n2 = n1==n2
>	_ == _ = False

Nat zu Instance Ord

>instance Ord Nat where
>	(N n1) `compare` (N n2) = n1 `compare` n2

Nat zu Instanz von Num 

>instance Num Nat where
>	(+)	(Null) (Null) = Null
>	(+) (N n) (Null) = N n
>	(+) (Null) (N n) = N n
>	(+) (N n1) (N n2) = N ((+) (N n1) n2)
>	(-) (N n) (Null) = N n
>	(-) (Null) _ = Null
>	(-) (N n1) (N n2) = n1 - n2
>	(*) (Null) _ = Null
>	(*) _ (Null) = Null
>	(*) (N n1) (N n2) = (+) (N n1) ((*) (N n1) n2)
>	abs (N n) = N n
>	fromInteger n -- Wandelt Integer in Type Nat
>		| n >= 1 = N (fromInteger(n-1))
>		| otherwise = Null
>	signum (N n) = N Null
>	signum (Null) = Null


Nat zu Instance von Enum

>instance Enum Nat where
>	toEnum n
>		| n <= 0 = Null
>		| otherwise = N (toEnum(n-1))
>	fromEnum (Null) = 0
>	fromEnum (N n) = 1 + (fromEnum n)

>toBinary :: Int -> [Char]
>toBinary n
>	| n < 2 = show(mod n 2)
>	| otherwise = toBinary(div n 2) ++ show(mod n 2)



Aufgabe 2

>type Wahrheitswert = Bool
>data Name = N1 | N2 | N3 | N4 | N5 deriving (Eq,Ord,Enum,Show)
>newtype Variable = Var Name deriving (Eq,Ord,Show)
>instance Enum Variable where
>	fromEnum (Var name) = fromEnum name
>	toEnum n = Var (toEnum n :: Name)
>data Ausdruck = K Wahrheitswert -- Logische Konstante
>	| V Variable -- Logische Variable
>	| Nicht Ausdruck -- Logische Negation
>	| Und Ausdruck Ausdruck -- Logische Konjunktion
>	| Oder Ausdruck Ausdruck -- Logische Disjunktion
>	deriving (Eq,Show)
>type Belegung = Variable -> Wahrheitswert



>auswerten :: Ausdruck -> Belegung -> Wahrheitswert
>auswerten (K a) _ = a -- Rueckgabe Wahrscheitswert
>auswerten (V a) b = b a -- Rueckgabe Wahrscheitswert von Belegung fuer die Variable
>auswerten (Nicht a) b = not(auswerten a b) -- Rekursivaufruf mit not 
>auswerten (Und a1 a2) b = (auswerten a1 b) && (auswerten a2 b) -- 2 Rekursivaufrufe und verknuepft
>auswerten (Oder a1 a2) b = (auswerten a1 b) || (auswerten a2 b) -- 2 Rekursivaufrufe oder verknuepft



>testBelegung :: Variable -> Wahrheitswert
>testBelegung a
>	| a == (Var N1) = True
>	| a == (Var N2) = False