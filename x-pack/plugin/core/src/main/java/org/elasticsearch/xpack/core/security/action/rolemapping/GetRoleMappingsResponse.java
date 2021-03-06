/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.core.security.action.rolemapping;

import java.io.IOException;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.xpack.core.security.authc.support.mapper.ExpressionRoleMapping;

/**
 * Response to {@link GetRoleMappingsAction get role-mappings API}.
 *
 * see org.elasticsearch.xpack.security.authc.support.mapper.NativeRoleMappingStore
 */
public class GetRoleMappingsResponse extends ActionResponse {

    private ExpressionRoleMapping[] mappings;

    public GetRoleMappingsResponse(ExpressionRoleMapping... mappings) {
        this.mappings = mappings;
    }

    public ExpressionRoleMapping[] mappings() {
        return mappings;
    }

    public boolean hasMappings() {
        return mappings.length > 0;
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        super.readFrom(in);
        int size = in.readVInt();
        mappings = new ExpressionRoleMapping[size];
        for (int i = 0; i < size; i++) {
            mappings[i] = new ExpressionRoleMapping(in);
        }
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        out.writeVInt(mappings.length);
        for (ExpressionRoleMapping mapping : mappings) {
            mapping.writeTo(out);
        }
    }
}
