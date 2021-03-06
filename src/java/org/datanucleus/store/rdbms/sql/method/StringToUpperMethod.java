/**********************************************************************
Copyright (c) 2008 Andy Jefferson and others. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Contributors:
    ...
**********************************************************************/
package org.datanucleus.store.rdbms.sql.method;

import java.util.List;

import org.datanucleus.exceptions.NucleusException;
import org.datanucleus.store.rdbms.sql.expression.SQLExpression;
import org.datanucleus.store.rdbms.sql.expression.StringLiteral;

/**
 * Expression handler to invoke the SQL UPPER function.
 * For use in evaluating StringExpression.toUpperCase() where the RDBMS supports this function.
 * Returns a StringExpression "UPPER({stringExpr})".
 */
public class StringToUpperMethod extends SimpleStringMethod
{
    protected String getFunctionName()
    {
        return "UPPER";
    }

    /* (non-Javadoc)
     * @see org.datanucleus.store.rdbms.sql.method.SQLMethod#getExpression(org.datanucleus.store.rdbms.sql.expression.SQLExpression, java.util.List)
     */
    public SQLExpression getExpression(SQLExpression expr, List args)
    {
        if (args != null && !args.isEmpty())
        {
            throw new NucleusException(LOCALISER.msg("060015", "toUpperCase", "StringExpression"));
        }

        if (!expr.isParameter() && expr instanceof StringLiteral)
        {
            String val = (String)((StringLiteral)expr).getValue();
            if (val != null)
            {
                val = val.toUpperCase();
            }
            return new StringLiteral(stmt, expr.getJavaTypeMapping(), val, null);
        }

        return super.getExpression(expr, null);
    }
}