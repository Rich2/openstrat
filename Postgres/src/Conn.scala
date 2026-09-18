/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gres
import utiljvm.*, geom.*, pweb.*, webjvm.*, java.sql.{ DriverManager, Connection }

val connStr = "jdbc:postgresql://localhost:5432/"

def postgresConn(name: String, password: String): Connection = DriverManager.getConnection(connStr, name, password)

/** Postgres table. */
case class Gable(name: String)
{
  def insert(valuesStr: String)(using conn: Connection): ExcEither[Int] =
  {
    try {
      val stmt = conn.createStatement()
      val result = stmt.executeUpdate("INSERT INTO" -- name -- valuesStr)
      Right(result)
    }
    catch {
      case e: Exception => Left(e)
    }
  }
}