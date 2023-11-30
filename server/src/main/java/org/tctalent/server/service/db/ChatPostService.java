/*
 * Copyright (c) 2023 Talent Beyond Boundaries.
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

package org.tctalent.server.service.db;

import java.util.List;
import org.springframework.lang.NonNull;
import org.tctalent.server.exception.NoSuchObjectException;
import org.tctalent.server.model.db.ChatPost;
import org.tctalent.server.model.db.JobChat;
import org.tctalent.server.model.db.chat.Post;

public interface ChatPostService {

    ChatPost createPost(@NonNull Post post, @NonNull JobChat jobChat);

    /**
     * Get the ChatPost with the given id.
     * @param id Id of post to get
     * @return ChatPost
     * @throws NoSuchObjectException if there is no post with this id.
     */
    @NonNull
    ChatPost getChatPost(long id) throws NoSuchObjectException;

    List<ChatPost> listChatPosts(long chatId);
}