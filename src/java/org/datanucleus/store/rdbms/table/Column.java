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
package org.datanucleus.store.rdbms.table;

import org.datanucleus.metadata.AbstractMemberMetaData;
import org.datanucleus.metadata.ColumnMetaData;
import org.datanucleus.store.rdbms.RDBMSStoreManager;
import org.datanucleus.store.rdbms.exceptions.ColumnDefinitionException;
import org.datanucleus.store.rdbms.identifier.DatastoreIdentifier;
import org.datanucleus.store.rdbms.mapping.datastore.DatastoreMapping;
import org.datanucleus.store.rdbms.mapping.java.JavaTypeMapping;
import org.datanucleus.store.rdbms.schema.RDBMSColumnInfo;
import org.datanucleus.store.rdbms.schema.SQLTypeInfo;

/**
 * Interface for a column in an RDBMS datastore.
 */
public interface Column
{
    /** wrapper function select **/
    public static final int WRAPPER_FUNCTION_SELECT = 0;

    /** wrapper function insert **/
    public static final int WRAPPER_FUNCTION_INSERT = 1;

    /** wrapper function update **/
    public static final int WRAPPER_FUNCTION_UPDATE = 2;

    /**
     * Accessor for the StoreManager for this table.
     * @return The StoreManager.
     */
    RDBMSStoreManager getStoreManager();

    /**
     * Accessor for the type of data stored in this field.
     * @return The type of data in the field.
     */
    String getStoredJavaType();

    /**
     * Mutator for the identifier of the column.
     * @param identifier The identifier
     */
    void setIdentifier(DatastoreIdentifier identifier);

    /**
     * Accessor for the identifier for this object.
     * @return The identifier.
     */
    DatastoreIdentifier getIdentifier();

    /**
     * Mutator to make the field the primary key.
     */
    void setAsPrimaryKey();

    /**
     * Accessor for whether the field is the primary key in the datastore.
     * @return whether the field is (part of) the primary key
     */
    boolean isPrimaryKey();

    /**
     * Mutator for the nullability of the datastore field.
     * @return The datastore field with the updated info
     */
    Column setNullable();

    /**
     * Accessor for whether the field is nullable in the datastore.
     * @return whether the field is nullable
     */
    boolean isNullable();

    /**
     * Mutator for the defaultability of the datastore field.
     * @return The datastore field with the updated info
     */
    Column setDefaultable(); 

    /**
     * Accessor for whether the column is defaultable.
     * @return whether the column is defaultable
     */
    boolean isDefaultable();

    /**
     * Mutator for the uniqueness of the column.
     * @return The datastore field with the updated info
     */
    Column setUnique();

    /**
     * Accessor for whether the column is unique.
     * @return whether the column is unique
     */
    boolean isUnique();

    /**
     * Mutator for whether we set this column as an identity column.
     * An "identity" column is typically treated differently in the datastore being
     * given a value by the datastore itself.
     * In RDBMS this would mean that the column is "AUTO_INCREMENT", "SERIAL" or 
     * @param identity True if column is identity
     */
    Column setIdentity(boolean identity);

    /**
     * Accessor for the whether this column is an identity column.
     * @return true if column is identity.
     */
    boolean isIdentity();

    /**
     * Mutator for the default Value
     * @param object default value
     */
    void setDefaultValue(Object object);

    /**
     * Accessor for the default Value
     * @return the default value
     */
    Object getDefaultValue();

    /**
     * Method to associate this datastore field with its mapping.
     * @param mapping The mapping for this datastore field
     */
    void setDatastoreMapping(DatastoreMapping mapping);

    /**
     * Accessor for the datastore mapping that this datastore field relates to.
     * @return The datastore mapping
     */
    DatastoreMapping getDatastoreMapping();

    /**
     * Method to set the MetaData for this datastore field.
     * Should only be called before completion of initialisation.
     * @param md The MetaData
     */
    void setColumnMetaData(ColumnMetaData md);

    /**
     * Access the metadata definition defining this column.
     * @return the MetaData
     */
    ColumnMetaData getColumnMetaData();

    /**
     * Accessor for the JavaTypeMapping for the field/property that owns this column.
     * @return The JavaTypeMapping
     */
    JavaTypeMapping getJavaTypeMapping();
    
    /**
     * Accessor for the table for this column
     * @return The table
     */
    Table getTable();
    
    /**
     * Wraps the column name with a FUNCTION.
     * <PRE>example: SQRT(?) generates: SQRT(columnName)</PRE>
     * @param replacementValue the replacement to ?. Probably it's a column name, that may be fully qualified name or not
     * @return a String with function taking as parameter the replacementValue
     */
    String applySelectFunction(String replacementValue);

    /**
     * Copy the configuration of this field to another field
     * @param col the column to copy
     */
    void copyConfigurationTo(Column col);

    /**
     * Accessor for the MetaData of the field/property that this is the datastore field for.
     * @return MetaData of the field/property (if representing a field/property of a class).
     */
    AbstractMemberMetaData getMemberMetaData();

    /**
     * Convenience method to check if the length is required to be unlimited (BLOB/CLOB).
     * @return Whether unlimited length required.
     */
    boolean isUnlimitedLength();

    /**
     * Mutator for the type information of the column.
     * @param typeInfo The type info
     * @return The column with the updated info
     */
    Column setTypeInfo(SQLTypeInfo typeInfo);

    /**
     * Accessor for the type info for this column.
     * @return The type info
     */
    SQLTypeInfo getTypeInfo();

    /**
     * Accessor for the JDBC type being used for this Column
     * @return The JDBC data type
     */
    int getJdbcType();

    /**
     * Accessor for the SQL definition of this column.
     * @return The SQL definition of the column
     */
    String getSQLDefinition();

    /**
     * Initialize the default column value and auto increment
     * @param ci The column information
     */
    void initializeColumnInfoFromDatastore(RDBMSColumnInfo ci);

    /**
     * Method to validate the contents of the column. This method can throw
     * IncompatibleDataTypeException, WrongScaleException,
     * WrongPrecisionException, IsNullableException if the data in the column is
     * not compatible with the supplied ColumnInfo.
     * @param ci The column information taken from the database
     */
    void validate(RDBMSColumnInfo ci);

    /**
     * Mutator for the constraints of the column.
     * @param constraints The constraints
     * @return The column with the updated info
     */
    Column setConstraints(String constraints);

    /**
     * @return Returns the constraints.
     */
    String getConstraints();

    /**
     * Checks the column definition as a primitive.
     * @throws ColumnDefinitionException
     */
    void checkPrimitive() throws ColumnDefinitionException;

    /**
     * Checks the column definition as an integer.
     * @throws ColumnDefinitionException
     */
    void checkInteger() throws ColumnDefinitionException;

    /**
     * Checks the column definition as a decimal.
     * @throws ColumnDefinitionException
     */
    void checkDecimal() throws ColumnDefinitionException;

    /**
     * Checks the column definition as a string.
     * @throws ColumnDefinitionException
     */
    void checkString() throws ColumnDefinitionException;

    /**
     * Sets a function to wrap the column. 
     * The wrapper function String must use "?" to be replaced later by the column name.
     * For example <pre>SQRT(?) generates: SQRT(COLUMN)</pre>
     * @param wrapperFunction The wrapperFunction to set.
     * @param wrapperMode whether select, insert or update
     */
    void setWrapperFunction(String wrapperFunction, int wrapperMode);

    /**
     * Gets the wrapper for parameters.
     * @param wrapperMode whether select, insert or update
     * @return Returns the wrapperFunction.
     */
    String getWrapperFunction(int wrapperMode);
}