/*
 * Copyright (c) 2021 Talent Beyond Boundaries.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License 
 * along with this program. If not, see https://www.gnu.org/licenses/.
 */

package org.tbbtalent.server.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test EnumHelper
 *
 * @author John Cameron
 */
public class TestEnumHelper {
    List<Fred> fredList;
    
    @BeforeEach
    void init() {
        fredList = new ArrayList<>();
        fredList.add(Fred.A);
        fredList.add(Fred.C);
    }
    
    @Test
    void testEnumArray() {
        String s = EnumHelper.toString(fredList);
        assertNotNull(s);
        
        List<Fred> x = EnumHelper.fromString(Fred.class, s);
        assertNotNull(x);
        
    }
    
    enum Fred {
        A,B,C
    }
}
