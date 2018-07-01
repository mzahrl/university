Aufgabe 1
Main function schuerfen:
Takes the max value of the list and generates a list of all tripleprimes
up until the max value of the list, and checks every element if it is in
the triplerimes list

>type Zahlenliste    = [Integer]
>type Tripelprimzahl = Integer

>schuerfen :: Zahlenliste -> [Tripelprimzahl]
>schuerfen []    = []
>schuerfen (n:ns)  
>	| n >= [primesnum | primesnum <- list, elem primesnum (triples (take 3 primes, (maximumInt list), 1))]

>maximumInt :: [Integer] -> Integer
>maximumInt []     = 0
>maximumInt (n:ns) = max n (maximumInt ns)

>triples :: ([Integer], Integer, Int) -> [Tripelprimzahl]
>triples (primnums, n, i)
>   | (primnums!!0 * primnums!!1 * primnums!!2) > n = []
>   | otherwise       = primnums!!0 * primnums!!1 * primnums!!2 : triples (take 3 (drop i primes ), n, i+1)

>primes :: [Integer]
>primes = sieve [2..]
>sieve :: [Integer] -> [Integer]
>sieve (x:xs) = x : sieve [y | y <- xs, mod y x > 0]

Aufgabe 2

>newtype Kurs = K Float deriving (Eq,Ord,Show)
>newtype Pegelstand = Pgl Float deriving (Eq,Ord,Show)

Addition, Minus, Multiplikation, absolute value, negation
fromInteger and signum functions are added to Type Kurs 

>instance Num Kurs where
>	(+) (K a) (K b) = K (a + b)
>	(-) (K a) (K b) = K (a - b)
>	(*) (K a) (K b) = K (a * b)
>	abs (K a) = K $ abs a
>	negate (K a) = K (negate a)
>	fromInteger a = K (fromInteger a)
>	signum (K a)
>		| a < 0 	= K (fromRational(-1))
>		| a == 0 	= K (fromRational 0)
>		| otherwise = K (fromRational 1) 

Addition, Minus, Multiplikation, absolute value, negation
fromInteger and signum functions are added to Type Pegelstand


>instance Num Pegelstand where
>	(+) (Pgl a) (Pgl b) = Pgl (a + b)
>	(-) (Pgl a) (Pgl b) = Pgl (a - b)
>	(*) (Pgl a) (Pgl b) = Pgl (a * b)
>	abs (Pgl a) = Pgl $ abs a
>	negate (Pgl a) = Pgl (negate a)
>	fromInteger a = Pgl (fromInteger a)
>	signum (Pgl a)
>		| a < 0 	= Pgl (fromRational(-1))
>		| a == 0 	= Pgl (fromRational 0)
>		| otherwise = Pgl (fromRational 1)




Aufgabe 3

currifies a function: x y z will be put together to (x,y,z) and processed by f

>curry3 :: ((a,b,c) -> d) -> a -> b -> c -> d
>curry3 f x y z = f (x,y,z)


uncurrifies a function: (x,y,z) will be put together to x y z and processed by g

>uncurry3 :: (a -> b -> c -> d) -> (a,b,c) -> d
>uncurry3 g (x,y,z) = g x y z


currifies a function and changes the postion of the arguments

>curry_flip :: ((a,b) -> c) -> (b -> a -> c)
>curry_flip f x y = f (y,x)


uncurrifies a function and changes the postion of the arguments

>uncurry_flip :: (a -> b -> c) -> ((b,a) -> c)
>uncurry_flip g (x,y) = g y x


functions for testing of the curry function

>currytest :: Integer -> Integer -> Integer -> Integer
>currytest a b c = a+b-c


>uncurrytest :: (Integer, Integer, Integer) -> Integer
>uncurrytest (a,b,c) = a+b-c


Aufgabe 4
Main function verflechten3: First checks If all lists are not empty
then if one is empty and after that if two are empty
The function always takes the first elemt of the lists and calls the function rekursiv again with
the rest of the lists

>verflechten3 :: [Int] -> [Int] -> [Int] -> [Int]
>verflechten3 (x:xs) (y:ys) (z:zs) = x : y : z : verflechten3 xs ys zs
>verflechten3 [] (y:ys) (z:zs) = y : z : verflechten3 [] ys zs 
>verflechten3 (x:xs) [] (z:zs) = x : z : verflechten3 xs [] zs 
>verflechten3 (x:xs) (y:ys) [] = x : y : verflechten3 xs ys []
>verflechten3 x [] [] = x
>verflechten3 [] [] z = z
>verflechten3 [] y [] = y