
let rec pow x n = 
  if n = 0 then 1
  else x * pow x (n - 1);;

let rec float_pow x n = 
  if n = 0 then 1.0
  else x *. float_pow x (n - 1);;

let rec compress list =
  match list with 
  | [] -> [] 
  | [singleElement] -> [singleElement]
  | currentElement :: (nextElement :: remainingElements) -> 
      if nextElement = currentElement 
      then compress (nextElement :: remainingElements) 
      else currentElement :: compress (nextElement :: remainingElements);;

let rec remove_if list predicate =
  match list with
  | [] -> []
  | currentElement :: remainingElements ->
      if predicate currentElement
      then remove_if remainingElements predicate
      else currentElement :: remove_if remainingElements predicate;;

let slice list i k =
  let rec sliceFront numLeftToSlice list = 
    match list with
    | [] -> []
    | currentElement :: remainingElements -> 
        if numLeftToSlice = 0 
        then list 
        else sliceFront (numLeftToSlice - 1) remainingElements 
  in
  let rec dontSlice numLeftNoSlice listAfterFrontSlice =
    match listAfterFrontSlice with
    | [] -> []
    | currentElement :: remainingElements -> 
        if numLeftNoSlice <= 0 
        then []
        else currentElement::dontSlice (numLeftNoSlice - 1) remainingElements
  in
  dontSlice(k - i) (sliceFront i list);;

let equivs equivsFun list = 
  let rec matchEquiv equivsFun head tail result =
    match tail with
    | [] -> (head::(List.rev result))
    | h::t ->
        if equivsFun head h
        then matchEquiv equivsFun head t (h::result)
        else matchEquiv equivsFun head t result
  in
  let rec negMatchEquiv equivsFun head tail result = 
    match tail with
    | [] -> List.rev result
    | h::t ->
        if equivsFun head h
        then negMatchEquiv equivsFun head t result
        else negMatchEquiv equivsFun head t (h::result)
  in
  let rec equivsHelper equivsFun list nestList = 
    match list with
    | [] -> List.rev nestList
    | head::tail ->
        equivsHelper equivsFun (negMatchEquiv equivsFun head tail []) 
          ((matchEquiv equivsFun head tail [])::nestList) 
  in equivsHelper equivsFun list [];;

let goldbachpair inputNum =
  let is_prime inputNum = 
    let rec isPrimeHelper currentNum = 
      (not(inputNum mod currentNum = 0) && isPrimeHelper (currentNum + 1)) 
      || currentNum * 2 > inputNum
    in
    isPrimeHelper 2
  in
  let rec inputCheck num =
    if is_prime num && is_prime (inputNum - num) 
    then (num, inputNum - num)
    else inputCheck (num + 1) 
  in
  inputCheck 2;;

let rec equiv_on f g list =
  match list with
  | [] -> true
  | currentElement::remainingElements ->
      if f currentElement = g currentElement
      then equiv_on f g remainingElements
      else false;;

let pairwisefilter cmp list = 
  let rec pwfHelper cmp inputList returnList =
    match inputList with 
    | [] -> []
    | [singleElementBcOdd] -> [singleElementBcOdd]
    | firstElement :: secondElement :: remainingElements ->
        (cmp firstElement secondElement) ::
        (pwfHelper cmp remainingElements returnList)
  in pwfHelper cmp list [];;

let polynomial listOfTuples = fun inputNum ->
  let rec polyHelper listOfTuples = 
    match listOfTuples with
    | [] -> 0 
    | (coeffElement,exponElement)::remainingElements -> 
        (coeffElement * 
         int_of_float (float_of_int inputNum ** float_of_int exponElement)) +
        polyHelper remainingElements
  in polyHelper listOfTuples;;

let powerset inputList = 
  let rec powersetHelperTwo list currentElement =
    match list with
    | [] -> []
    | head :: tail->
        (List.rev(currentElement :: head)) :: 
        (powersetHelperTwo tail currentElement)
  in
  let rec powersetHelper inputlist =
    match inputlist with
    | [] -> [[]]
    | currentElement :: remainingElements-> 
        (powersetHelper remainingElements) @ 
        (powersetHelperTwo (powersetHelper remainingElements) currentElement)
  in powersetHelper (List.rev inputList);;