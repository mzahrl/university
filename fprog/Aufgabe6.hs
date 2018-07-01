type Wahrheitswert = Bool
data VName = N1 | N2 | N3 | N4 | N5 deriving (Eq,Ord,Enum,Show)
newtype Variable = Var VName deriving (Eq,Ord,Show)


instance Enum Variable where
	fromEnum (Var name) = fromEnum name
	toEnum n = Var (toEnum n :: VName)




data Ausdruck = K Wahrheitswert -- Logische Konstante
	| V Variable -- Logische Variable
	| Nicht Ausdruck -- Logische Negation
	| Und Ausdruck Ausdruck -- Logische Konjunktion
	| Oder Ausdruck Ausdruck -- Logische Disjunktion
	| Impl Ausdruck Ausdruck -- Logische Implikation
	| Esgibt Variable Ausdruck -- Existentiell quantifizierter Ausdruck
	| Fueralle Variable Ausdruck -- Allquantifizierter Ausdruck
	deriving (Eq,Show)
type Belegung = Variable -> Wahrheitswert -- Total definierte Abbildung


evaluiere :: Ausdruck -> Belegung -> Wahrheitswert
evaluiere (K a) _ = a -- Rueckgabe Wahrscheitswert
evaluiere (V a) b = b a -- Rueckgabe Wahrscheitswert von Belegung fuer die Variable
evaluiere (Nicht a) b = not(evaluiere a b) -- Rekursivaufruf mit not 
evaluiere (Und a1 a2) b = (evaluiere a1 b) && (evaluiere a2 b) -- 2 Rekursivaufrufe und verknuepft
evaluiere (Oder a1 a2) b = (evaluiere a1 b) || (evaluiere a2 b) -- 2 Rekursivaufrufe oder verknuepft
evaluiere (Impl a1 a2) b = (not(evaluiere a1 b)) || (evaluiere a2 b)
evaluiere (Esgibt v a) b = (evaluiere a b) || (evaluiere a (vertauscheBel b v))
evaluiere (Fueralle v a) b = (evaluiere a b) && (evaluiere a (vertauscheBel b v))


 


vertauscheBel :: Belegung -> Variable -> Variable -> Wahrheitswert
vertauscheBel b v1 v2
	| v1 == v2 = not(b v2)
	| otherwise = b v2

-- Bilden einer KNF, wahrheitsbelegung ueberpruefen, oder alle negierungen und verknuepfen
ist_tautologie :: Ausdruck -> Ausdruck -> Wahrheitswert
ist_tautologie a1 a2 = evaluiere (Fueralle(Var N1) (Fueralle(Var N2) (Fueralle(Var N3) (Fueralle(Var N4) (Fueralle (Var N5) (Oder (Und a1 a2) (Und (Nicht a1) (Nicht a2)))))))) testBelegung

schreibe :: Ausdruck -> String
schreibe (K True) = "wahr"
schreibe (K False) = "falsch"
schreibe (V (Var n)) = show(n)
schreibe (Nicht a) = "("++"neg"++" "++schreibe a++")"
schreibe (Und a1 a2) = "("++schreibe a1++" "++"und"++" "++schreibe a2++")"
schreibe (Oder a1 a2) = "("++schreibe a1++" "++"oder"++" "++schreibe a2++")"
schreibe (Impl a1 a2) = "("++schreibe a1++" "++"=>"++" "++schreibe a2++")" 
schreibe (Esgibt v a) =  "("++"EG"++" "++schreibe (V v)++"."++" "++schreibe a++")"
schreibe (Fueralle v a) = "("++"FA"++" "++schreibe (V v)++"."++" "++schreibe a++")"


testBelegung :: Variable -> Wahrheitswert
testBelegung a
	| a == (Var N1) = True
	| a == (Var N2) = False
	| a == (Var N3) = True
	| a == (Var N4) = False
	| a == (Var N5) = True






