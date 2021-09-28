type expr = 
  | Const of int 
  | Var of string
  | Plus of twoArgs
  | Mult of twoArgs
  | Minus of twoArgs
  | Div of twoArgs 
and twoArgs = {arg1 : expr; arg2 : expr};; 
  
let rec evaluate expr =
  match expr with
  | Const integer -> integer
  | Plus {arg1; arg2} -> (evaluate arg1) + (evaluate arg2)
  | Mult {arg1; arg2} -> (evaluate arg1) * (evaluate arg2)
  | Minus {arg1; arg2} -> (evaluate arg1) - (evaluate arg2)
  | Div {arg1; arg2} -> (evaluate arg1) / (evaluate arg2);;