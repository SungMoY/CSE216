type bool_expr =
  | Lit of string
  | Not of bool_expr
  | And of bool_expr * bool_expr
  | Or of bool_expr * bool_expr 

let rec expressionVal literalOne literalTwo expression 
    literalOneVal literalTwoVal = 
  match expression with
  | Lit string ->
      if string = literalOne
      then literalOneVal
      else literalTwoVal
  | Not bool_expr ->
      not(expressionVal literalOne literalTwo bool_expr literalOneVal literalTwoVal)
  | And (bool_exprOne, bool_exprTwo) ->
      (expressionVal literalOne literalTwo bool_exprOne literalOneVal literalTwoVal) && 
      (expressionVal literalOne literalTwo bool_exprTwo literalOneVal literalTwoVal)      
  | Or (bool_exprOne, bool_exprTwo) ->
      (expressionVal literalOne literalTwo bool_exprOne literalOneVal literalTwoVal) || 
      (expressionVal literalOne literalTwo bool_exprTwo literalOneVal literalTwoVal) 
      
let truth_table literalOne literalTwo expression =
  [(true, true, expressionVal literalOne literalTwo expression true true);
   (true, false, expressionVal literalOne literalTwo expression true false);
   (false, true, expressionVal literalOne literalTwo expression false true);
   (false, false, expressionVal literalOne literalTwo expression false false)];;