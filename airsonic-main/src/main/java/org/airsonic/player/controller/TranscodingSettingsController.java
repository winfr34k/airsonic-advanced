/*
 This file is part of Airsonic.

 Airsonic is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Airsonic is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Airsonic.  If not, see <http://www.gnu.org/licenses/>.

 Copyright 2023 (C) Y.Tory
 Copyright 2016 (C) Airsonic Authors
 Based upon Subsonic, Copyright 2009 (C) Sindre Mehus
 */
package org.airsonic.player.controller;

import org.airsonic.player.config.AirsonicHomeConfig;
import org.airsonic.player.domain.Transcoding;
import org.airsonic.player.service.SettingsService;
import org.airsonic.player.service.TranscodingService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for the page used to administrate the set of transcoding configurations.
 *
 * @author Sindre Mehus
 */
@Controller
@RequestMapping("/transcodingSettings")
public class TranscodingSettingsController {

    @Autowired
    private TranscodingService transcodingService;
    @Autowired
    private SettingsService settingsService;
    @Autowired
    private AirsonicHomeConfig homeConfig;

    @GetMapping
    public String doGet(Model model) {

        Map<String, Object> map = new HashMap<>();

        map.put("transcodings", transcodingService.getAllTranscodings());
        map.put("transcodeDirectory", homeConfig.getTranscodeDirectory());
        map.put("splitOptions", settingsService.getSplitOptions());
        map.put("splitCommand", settingsService.getSplitCommand());
        map.put("downsampleCommand", settingsService.getDownsamplingCommand());
        map.put("hlsCommand", settingsService.getHlsCommand());
        map.put("jukeboxCommand", settingsService.getJukeboxCommand());
        map.put("videoImageCommand", settingsService.getVideoImageCommand());
        map.put("subtitlesExtractionCommand", settingsService.getSubtitlesExtractionCommand());
        map.put("transcodeEstimateTimePadding", settingsService.getTranscodeEstimateTimePadding());
        map.put("transcodeEstimateBytePadding", settingsService.getTranscodeEstimateBytePadding());
        map.put("brand", settingsService.getBrand());

        model.addAttribute("model", map);
        return "transcodingSettings";
    }

    @PostMapping
    public String doPost(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String error = handleParameters(request, redirectAttributes);
        if (error != null) {
            redirectAttributes.addFlashAttribute("error", error);
        } else {
            redirectAttributes.addFlashAttribute("settings_toast", true);
        }
        return "redirect:transcodingSettings.view";
    }

    private String handleParameters(HttpServletRequest request, RedirectAttributes redirectAttributes) {

        for (Transcoding transcoding : transcodingService.getAllTranscodings()) {
            Integer id = transcoding.getId();
            String name = getParameter(request, "name", id);
            String sourceFormats = getParameter(request, "sourceFormats", id);
            String targetFormat = getParameter(request, "targetFormat", id);
            String step1 = getParameter(request, "step1", id);
            String step2 = getParameter(request, "step2", id);
            boolean delete = getParameter(request, "delete", id) != null;

            if (delete) {
                transcodingService.deleteTranscoding(id);
            } else if (name == null) {
                return "transcodingsettings.noname";
            } else if (sourceFormats == null) {
                return "transcodingsettings.nosourceformat";
            } else if (targetFormat == null) {
                return "transcodingsettings.notargetformat";
            } else if (step1 == null) {
                return "transcodingsettings.nostep1";
            } else {
                transcoding.setName(name);
                transcoding.setSourceFormats(sourceFormats);
                transcoding.setTargetFormat(targetFormat);
                transcoding.setStep1(step1);
                transcoding.setStep2(step2);
                transcodingService.updateTranscoding(transcoding);
            }
        }

        String name = StringUtils.trimToNull(request.getParameter("name"));
        String sourceFormats = StringUtils.trimToNull(request.getParameter("sourceFormats"));
        String targetFormat = StringUtils.trimToNull(request.getParameter("targetFormat"));
        String step1 = StringUtils.trimToNull(request.getParameter("step1"));
        String step2 = StringUtils.trimToNull(request.getParameter("step2"));
        boolean defaultActive = request.getParameter("defaultActive") != null;

        if (name != null || sourceFormats != null || targetFormat != null || step1 != null || step2 != null) {
            Transcoding transcoding = new Transcoding(null, name, sourceFormats, targetFormat, step1, step2, null, defaultActive);
            String error = null;
            if (name == null) {
                error = "transcodingsettings.noname";
            } else if (sourceFormats == null) {
                error = "transcodingsettings.nosourceformat";
            } else if (targetFormat == null) {
                error = "transcodingsettings.notargetformat";
            } else if (step1 == null) {
                error = "transcodingsettings.nostep1";
            } else {
                transcodingService.createTranscoding(transcoding);
            }
            if (error != null) {
                redirectAttributes.addAttribute("newTranscoding", transcoding);
                return error;
            }
        }
        settingsService.setSplitOptions(StringUtils.trim(request.getParameter("splitOptions")));
        settingsService.setSplitCommand(StringUtils.trim(request.getParameter("splitCommand")));
        settingsService.setDownsamplingCommand(StringUtils.trim(request.getParameter("downsampleCommand")));
        settingsService.setHlsCommand(StringUtils.trim(request.getParameter("hlsCommand")));
        settingsService.setJukeboxCommand(StringUtils.trim(request.getParameter("jukeboxCommand")));
        settingsService.setVideoImageCommand(StringUtils.trim(request.getParameter("videoImageCommand")));
        settingsService.setSubtitlesExtractionCommand(StringUtils.trim(request.getParameter("subtitlesExtractionCommand")));

        String timePad = StringUtils.trimToNull(request.getParameter("transcodeEstimateTimePadding"));
        String bytePad = StringUtils.trimToNull(request.getParameter("transcodeEstimateBytePadding"));
        settingsService.setTranscodeEstimateTimePadding(timePad == null ? null : Long.parseLong(timePad));
        settingsService.setTranscodeEstimateBytePadding(bytePad == null ? null : Long.parseLong(bytePad));

        settingsService.save();
        return null;
    }

    private String getParameter(HttpServletRequest request, String name, Integer id) {
        return StringUtils.trimToNull(request.getParameter(name + "[" + id + "]"));
    }
}
