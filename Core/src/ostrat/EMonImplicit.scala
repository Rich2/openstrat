/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** Extension methods for EMon, the error Monad */
class EMonImplicit[A](val eMon: EMon[A]) extends AnyVal
{
   def errs: Seq[ParseErr] = eMon match
   {
      case Good(_) => Seq()
      case Bad(errs) => errs
   }
   
   def map2[A2, B](eMon2: EMon[A2], f: (A, A2) => B): EMon[B] = eMon match
   {
      case Good(a) => eMon2.map(a2 => f(a, a2))
      case Bad(errs1) => Bad[B](errs1 ++ eMon2.errs)      
   }
   
   def map3[A2, A3, B](eMon2: EMon[A2], eMon3: EMon[A3], f: (A, A2, A3) => B): EMon[B] = eMon match
   {
      case Bad(errs1) => Bad[B](errs1 ++ eMon2.errs ++ eMon3.errs)
      case Good(a1) => eMon2 match
      {
         case Bad(errs2) => Bad[B](errs2 ++ eMon3.errs)
         case Good(a2) => eMon3.map(a3 => f(a1, a2, a3))        
      }            
   }
   def map4[A2, A3, A4, B](eMon2: EMon[A2], eMon3: EMon[A3], eMon4: EMon[A4], f: (A, A2, A3, A4) => B): EMon[B] = eMon match
   {
      case Bad(errs1) => Bad[B](errs1 ++ eMon2.errs ++ eMon3.errs ++ eMon4.errs)
      case Good(a1) => eMon2 match
      {
         case Bad(errs2) => Bad[B](errs2 ++ eMon3.errs ++ eMon4.errs)
         case Good(a2) => eMon3 match
         {
            case Bad(errs3) => Bad[B](errs3 ++ eMon4.errs)
            case Good(a3) =>eMon4.map(a4 => f(a1, a2, a3, a4))        
         }
      }            
   }
}
