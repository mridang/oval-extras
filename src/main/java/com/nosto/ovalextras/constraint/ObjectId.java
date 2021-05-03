/*******************************************************************************
 * Copyright (c) 2018 Nosto Solutions Ltd All Rights Reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Nosto Solutions Ltd ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the agreement you entered into with
 * Nosto Solutions Ltd.
 ******************************************************************************/

package com.nosto.ovalextras.constraint;

import net.sf.oval.configuration.annotation.Constraint;

import java.lang.annotation.*;

/**
 * Checks if the value given is a correct ObjectId
 *
 * <br>
 * <b>Note:</b> This constraint is also satisfied when the value to validate is null, therefore you might also need to specified @NotNull
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE, ElementType.TYPE_USE, ElementType.METHOD})
@Constraint(checkWith = ObjectIdCheck.class)
public @interface ObjectId {

    /**
     * message to be used for the ContraintsViolatedException
     */
    String message() default "com.nosto.ovalextras.constraint.ObjectId.violated";
}
