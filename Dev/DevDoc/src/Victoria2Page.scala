/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*, wcode.*

object Victoria2Page extends OSDocumentationPage
{ override def titleStr: String = "Victoria 2"
  override def fileNameStem: String = "victoria2"
  override def body: HtmlBody = HtmlBody(franceMobilise)

  val franceMobilise = HtmlOl.strs(
    "Arras 18 > 21",
    "Cambrai 9 > 15 > 18",
    "Nancy 3 > 6 > 9 > 12 > 18",
    "Metz 3 > 6",
    "Caen 9 > 30",
    "Brest 6 > 12 > 18 > 21 > 27",
    "Paris 6 > 9 > 12",
    "Dijon 3 > 12 > 18 > 21",
    "Laval 12 > 15",
    "Orleans 6 > 12 > 15 > 18",
    "La Rochelle 6 > 9 > 12",
    "Limoge 3 > 9 > 18",
    "Clemont-Ferrand 9 > 12 > 15",
    "Lyon 9 > 18 > 27",
    "Toulouse 6 > 12 > 15",
    "Bordeaux 6 > 12 > 18",
    "Montpellier 9 > 15 > 18",
    "Marseille 3 > 6 > 9"
  )
}