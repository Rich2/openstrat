/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb; package webjvm
import utiljvm.*, pweb.*, pParse.*, java.sql.*, org.postgresql.util.PSQLException

trait PgDatabase
{  
  def tables: RArr[Gable]

  
}

object PgDatabase
{
  def apply(tables: Gable*): PgDatabase = PgDatabaseGen(tables.toRArr)

  def eSetts(connSettings: String): IOParseExcEither[(Identifier, Identifier, String)] =
    connSettings.find3Settings[Identifier, Identifier, String]("database", "appUsername", "appPassword")

  def conn(dbName: String, uName: String, password: String): PsqlExcEither[Connection] =
    try {
      Right(DriverManager.getConnection(connStr + dbName, uName, password))
    }
    catch {
      case sqlExc: PSQLException => Left(sqlExc)
    }

  def settConn(connSettings: String): Either[IOParseExc | PSQLException, Connection] =
    eSetts(connSettings).flatMap{ (dbName, uName, pWord) => postgresConn(dbName.srcStr, uName.srcStr, pWord) }
  
  case class PgDatabaseGen(tables: RArr[Gable]) extends PgDatabase
}